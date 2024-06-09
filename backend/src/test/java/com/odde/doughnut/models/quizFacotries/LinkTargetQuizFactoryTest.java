package com.odde.doughnut.models.quizFacotries;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.odde.doughnut.entities.LinkingNote;
import com.odde.doughnut.entities.Note;
import com.odde.doughnut.entities.QuizQuestion;
import com.odde.doughnut.entities.ReviewPoint;
import com.odde.doughnut.factoryServices.quizFacotries.factories.LinkTargetQuizFactory;
import com.odde.doughnut.models.UserModel;
import com.odde.doughnut.testability.MakeMe;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LinkTargetQuizFactoryTest {
  @Autowired MakeMe makeMe;
  UserModel userModel;
  Note top;
  Note target;
  Note source;
  Note anotherTarget;
  ReviewPoint reviewPoint;

  @BeforeEach
  void setup() {
    userModel = makeMe.aUser().toModelPlease();
    top = makeMe.aNote().creatorAndOwner(userModel).please();
    target = makeMe.aNote("target").under(top).please();
    source = makeMe.aNote("source").under(top).linkTo(target).please();
    anotherTarget = makeMe.aNote("another note").under(top).please();
    reviewPoint = makeMe.aReviewPointFor(source.getLinks().get(0)).inMemoryPlease();
  }

  @Test
  void shouldReturnNullIfCannotFindEnoughOptions() {
    makeMe.aLink().between(source, anotherTarget).please();

    assertThat(buildLinkTargetQuizQuestion(), is(nullValue()));
  }

  @Nested
  class WhenThereAreMoreThanOneOptions {
    @Test
    void shouldIncludeRightAnswers() {
      QuizQuestion quizQuestion = buildLinkTargetQuizQuestion();
      assertThat(
          quizQuestion.getMultipleChoicesQuestion().getStem(),
          equalTo("<mark>source</mark> is a specialization of:"));
      List<String> options = quizQuestion.getMultipleChoicesQuestion().getChoices();
      assertThat(anotherTarget.getTopicConstructor(), in(options));
      assertThat(target.getTopicConstructor(), in(options));
    }
  }

  private QuizQuestion buildLinkTargetQuizQuestion() {
    return makeMe.buildAQuestion(
        new LinkTargetQuizFactory((LinkingNote) reviewPoint.getNote()), reviewPoint);
  }
}
