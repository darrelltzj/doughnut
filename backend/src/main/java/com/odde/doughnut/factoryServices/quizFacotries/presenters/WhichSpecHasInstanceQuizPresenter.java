package com.odde.doughnut.factoryServices.quizFacotries.presenters;

import com.odde.doughnut.entities.Note;
import com.odde.doughnut.entities.quizQuestions.QuizQuestionWhichSpecHasInstance;

public class WhichSpecHasInstanceQuizPresenter extends QuizQuestionWithOptionsPresenter {
  private Note instanceLink;
  private final Note link;

  public WhichSpecHasInstanceQuizPresenter(QuizQuestionWhichSpecHasInstance quizQuestion) {
    super(quizQuestion);
    this.link = quizQuestion.getNote();
    this.instanceLink = quizQuestion.getCategoryLink();
  }

  @Override
  public String mainTopic() {
    return null;
  }

  @Override
  public String stem() {
    return "<p>Which one is "
        + link.getLinkType().label
        + " <mark>"
        + link.getTargetNote().getTopicConstructor()
        + "</mark> <em>and</em> is "
        + instanceLink.getLinkType().label
        + " <mark>"
        + instanceLink.getTargetNote().getTopicConstructor()
        + "</mark>:";
  }
}
