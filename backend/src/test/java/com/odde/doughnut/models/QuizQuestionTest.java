package com.odde.doughnut.models;

import com.odde.doughnut.entities.NoteEntity;
import com.odde.doughnut.models.randomizers.NonRandomizer;
import com.odde.doughnut.models.randomizers.RealRandomizer;
import com.odde.doughnut.testability.MakeMe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:repository.xml"})
@Transactional
class QuizQuestionTest {
    @Autowired
    MakeMe makeMe;
    UserModel userModel;
    NonRandomizer randomizer = new NonRandomizer();

    @BeforeEach
    void setup() {
        userModel = makeMe.aUser().toModelPlease();
    }

    @Test
    void aNoteWithNoDescriptionHasNoQuiz() {
        NoteEntity noteEntity = makeMe.aNote().withNoDescription().byUser(userModel).please();
        QuizQuestion quizQuestion = getQuizQuestion(noteEntity);
        assertThat(quizQuestion, is(nullValue()));
    }

    @Test
    void useClozeDescription() {
        NoteEntity noteEntity = makeMe.aNote().title("abc").description("abc has 3 letters").please();
        QuizQuestion quizQuestion = getQuizQuestion(noteEntity);
        assertThat(quizQuestion.getDescription(), equalTo("[...] has 3 letters"));
    }

    @Nested
    class ClozeSelectionQuiz {
        private List<String> getOptions(NoteEntity noteEntity) {
            QuizQuestion quizQuestion = getQuizQuestion(noteEntity);
            List<String> options = quizQuestion.getOptions().stream().map(o -> o.getDisplay()).collect(Collectors.toUnmodifiableList());
            return options;
        }

        @Test
        void aNoteWithNoSiblings() {
            NoteEntity noteEntity = makeMe.aNote().please();
            List<String> options = getOptions(noteEntity);
            assertThat(options, contains(noteEntity.getTitle()));
        }

        @Test
        void aNoteWithOneSibling() {
            NoteEntity top = makeMe.aNote().please();
            NoteEntity noteEntity1 = makeMe.aNote().under(top).please();
            NoteEntity noteEntity2 = makeMe.aNote().under(top).please();
            List<String> options = getOptions(noteEntity1);
            assertThat(options, containsInAnyOrder(noteEntity1.getTitle(), noteEntity2.getTitle()));
        }

        @Test
        void aNoteWithManySiblings() {
            NoteEntity top = makeMe.aNote().please();
            makeMe.theNote(top).with10Children().please();
            NoteEntity noteEntity = makeMe.aNote().under(top).please();
            List<String> options = getOptions(noteEntity);
            assertThat(options.size(), equalTo(6));
            assertThat(options.contains(noteEntity.getTitle()), is(true));
        }
    }

    @Nested
    class SpellingQuiz {
        NoteEntity note;

        @BeforeEach
        void setup() {
            note = makeMe.aNote().rememberSpelling().please();
        }

        @Test
        void typeShouldBeSpellingQuiz() {
            assertThat(getQuizQuestion(note).getQuestionType(), equalTo(QuizQuestion.QuestionType.SPELLING));
        }

        @Test
        void shouldReturnTheSameType() {
            ReviewPointModel reviewPoint = getReviewPointModel(note);
            QuizQuestion randomQuizQuestion = reviewPoint.generateAQuizQuestion(new RealRandomizer());
            Set<QuizQuestion.QuestionType> types = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                types.add(randomQuizQuestion.getQuestionType());
            }
            assertThat(types, hasSize(1));
        }

        @Test
        void shouldChooseTypeRandomly() {
            Set<QuizQuestion.QuestionType> types = new HashSet<>();
            ReviewPointModel reviewPoint = getReviewPointModel(note);
            for (int i = 0; i < 10; i++) {
                QuizQuestion randomQuizQuestion = reviewPoint.generateAQuizQuestion(new RealRandomizer());
                types.add(randomQuizQuestion.getQuestionType());
            }
            assertThat(types, containsInAnyOrder(QuizQuestion.QuestionType.SPELLING, QuizQuestion.QuestionType.CLOZE_SELECTION));
        }

    }

    private QuizQuestion getQuizQuestion(NoteEntity noteEntity) {
        return getReviewPointModel(noteEntity).generateAQuizQuestion(randomizer);
    }

    private ReviewPointModel getReviewPointModel(NoteEntity noteEntity) {
        return makeMe.aReviewPointFor(noteEntity).by(userModel).toModelPlease();
    }

}