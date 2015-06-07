package ro.unitbv.news.repository.converter;

import ro.unitbv.news.entity.FeedEntity;
import ro.unitbv.news.entity.NewsEntity;
import ro.unitbv.news.entity.UserEntity;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.User;

/**
 * Tool that converts model objects to entities or entities to model objects.
 *
 * @author Teodora Tanase
 */
public class ModelEntityConverter {

	/**
	 * Converts an entity user to a model user, without its followers.
	 *
	 * @param userEntity entity user to convert to a model user.
	 * @return the model user.
	 */
	public User toUserModel(UserEntity userEntity) {
		User userModel = new User();
		userModel.setId(userEntity.getId());
		userModel.setUsername(userEntity.getUsername());
		userModel.setPassword(userEntity.getPassword());
		return userModel;
	}

	/**
	 * Converts a model user to an entity user, without its followers.
	 *
	 * @param userModel model user to convert to an entity user.
	 * @return the entity user.
	 */
	public UserEntity toUserEntity(User userModel) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(userModel.getId());
		userEntity.setUsername(userModel.getUsername());
		userEntity.setPassword(userModel.getPassword());
		return userEntity;
	}

	/**
	 * Converts an entity feed to a model feed.
	 *
	 * @param feedEntity entity feed to convert to a model feed.
	 * @return the model feed.
	 */
	public Feed toFeedModel(FeedEntity feedEntity) {
		Feed feedModel = new Feed();
		feedModel.setId(feedEntity.getId());
		feedModel.setName(feedEntity.getName());
		feedModel.setUrl(feedEntity.getUrl());
		feedModel.setDescription(feedEntity.getDescription());
		feedModel.setOwnerId(feedEntity.getOwner().getId());
		return feedModel;
	}

	/**
	 * Converts a model feed to an entity feed without its owner.
	 *
	 * @param feedModel model feed to convert to an entity feed.
	 * @return the entity feed.
	 */
	public FeedEntity toFeedEntity(Feed feedModel) {
		FeedEntity feedEntity = new FeedEntity();
		feedEntity.setId(feedModel.getId());
		feedEntity.setName(feedModel.getName());
		feedEntity.setUrl(feedModel.getUrl());
		feedEntity.setDescription(feedModel.getDescription());
		return feedEntity;
	}

	/**
	 * Converts an entity news to a model news.
	 *
	 * @param newsEntity entity news to convert to a model news.
	 * @return the model news.
	 */
	public News toNewsModel(NewsEntity newsEntity) {
		News newsModel = new News();
		newsModel.setId(newsEntity.getId());
		newsModel.setOwnerId(newsEntity.getOwner().getId());
		newsModel.setFeedId(newsEntity.getFeed().getId());
		newsModel.setTitle(newsEntity.getTitle());
		newsModel.setContent(newsEntity.getContent());
		newsModel.setUrl(newsEntity.getUrl());
		newsModel.setDate(newsEntity.getDate());
		return newsModel;
	}

	/**
	 * Converts a model news to an entity news without its owner and feed.
	 *
	 * @param newsModel model news to convert to an entity news.
	 * @return the entity news.
	 */
	public NewsEntity toNewsEntity(News newsModel) {
		NewsEntity newsEntity = new NewsEntity();
		newsEntity.setId(newsModel.getId());
		newsEntity.setTitle(newsModel.getTitle());
		newsEntity.setContent(newsModel.getContent());
		newsEntity.setUrl(newsModel.getUrl());
		newsEntity.setDate(newsModel.getDate());
		return newsEntity;
	}
}
