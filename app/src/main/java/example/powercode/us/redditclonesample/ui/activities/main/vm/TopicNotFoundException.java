package example.powercode.us.redditclonesample.ui.activities.main.vm;

/**
 * @author meugen
 */
public class TopicNotFoundException extends Exception {

    private final Long topicId;

    TopicNotFoundException(final Long topicId) {
        this.topicId = topicId;
    }

    public Long getTopicId() {
        return topicId;
    }
}
