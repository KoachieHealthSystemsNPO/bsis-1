package helpers.builders;

import static helpers.builders.AdverseEventTypeBuilder.anAdverseEventType;
import helpers.persisters.AbstractEntityPersister;
import helpers.persisters.AdverseEventPersister;
import model.adverseevent.AdverseEvent;
import model.adverseevent.AdverseEventType;

public class AdverseEventBuilder extends AbstractEntityBuilder<AdverseEvent> {
    
    private Long id;
    private AdverseEventType type = anAdverseEventType().build();
    private String comment;

    public AdverseEventBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    
    public AdverseEventBuilder withType(AdverseEventType type) {
        this.type = type;
        return this;
    }
    
    public AdverseEventBuilder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public AdverseEvent build() {
        AdverseEvent adverseEvent = new AdverseEvent();
        adverseEvent.setId(id);
        adverseEvent.setType(type);
        adverseEvent.setComment(comment);
        return adverseEvent;
    }
    
    @Override
    public AbstractEntityPersister<AdverseEvent> getPersister() {
        return new AdverseEventPersister();
    }

    public static AdverseEventBuilder anAdverseEvent() {
        return new AdverseEventBuilder();
    }

}
