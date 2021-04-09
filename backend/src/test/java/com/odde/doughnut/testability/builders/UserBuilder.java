package com.odde.doughnut.testability.builders;

import com.odde.doughnut.entities.User;
import com.odde.doughnut.models.UserModel;
import com.odde.doughnut.testability.EntityBuilder;
import com.odde.doughnut.testability.MakeMe;

public class UserBuilder extends EntityBuilder<User> {
    static final TestObjectCounter exIdCounter = new TestObjectCounter(n -> "exid" + n);
    static final TestObjectCounter nameCounter = new TestObjectCounter(n -> "user" + n);
    protected final Class<UserModel> mClass;

    public UserBuilder(MakeMe makeMe) {
        super(makeMe, new User());
        this.mClass = UserModel.class;
        entity.setExternalIdentifier(exIdCounter.generate());
        entity.setName(nameCounter.generate());
    }

    public UserBuilder with2Notes() {
        entity.getNotes().add(makeMe.aNote().byUser(entity).inMemoryPlease());
        entity.getNotes().add(makeMe.aNote().byUser(entity).inMemoryPlease());
        return this;
    }

    public UserBuilder withSpaceIntervals(String spaceIntervals) {
        entity.setSpaceIntervals(spaceIntervals);
        return this;
    }

    public UserModel toModelPlease() {
        return makeMe.modelFactoryService.toModel(please(), mClass);
    }

    @Override
    public void beforeCreate(boolean needPersist) {

    }
}


