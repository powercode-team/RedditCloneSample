package example.powercode.us.redditclonesample.ui.activities.main.binding;

import java.util.List;

import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.ui.activities.base.binding.Binding;

/**
 * @author meugen
 */
public interface TopicListBinding extends Binding {

    void setupTopics(OnDeleteTopicListener listener);

    void setupListeners();

    void showItems(List<TopicEntity> items);

    void updateItemWithId(final long id);

    interface OnDeleteTopicListener {

        void onDeleteTopic(TopicEntity topic);
    }
}
