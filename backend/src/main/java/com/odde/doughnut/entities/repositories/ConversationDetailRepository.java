package com.odde.doughnut.entities.repositories;

import com.odde.doughnut.entities.ConversationDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationDetailRepository extends CrudRepository<ConversationDetail, Integer> {
  @Query("SELECT c FROM ConversationDetail c WHERE c.conversation.id = :conversationId")
  List<ConversationDetail> findByConversationInitiator(@Param("conversationId") int conversationId);
}
