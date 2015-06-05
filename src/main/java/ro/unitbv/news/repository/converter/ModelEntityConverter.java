package ro.unitbv.news.repository.converter;

/**
 * Tool that converts model objects to entities or entities to model objects.
 *
 * @author Teodora Tanase
 */
public class ModelEntityConverter {

	/**
	 * Converts an entity user to a model user.
	 *
	 * @param entityUser entity user to convert to a model user.
	 * @return the model user.
	 */
	public ro.unitbv.news.model.User toModelUser(ro.unitbv.news.entity.User entityUser) {
		ro.unitbv.news.model.User modelUser = new ro.unitbv.news.model.User();
		modelUser.setId(entityUser.getId());
		modelUser.setUsername(entityUser.getUsername());
		modelUser.setPassword(entityUser.getPassword());
		return modelUser;
	}

	/**
	 * Converts a model user to an entity user.
	 *
	 * @param modelUser model user to convert to an entity user.
	 * @return the entity user.
	 */
	public ro.unitbv.news.entity.User toEntityUser(ro.unitbv.news.model.User modelUser) {
		ro.unitbv.news.entity.User entityUser = new ro.unitbv.news.entity.User();
		entityUser.setId(modelUser.getId());
		entityUser.setUsername(modelUser.getUsername());
		entityUser.setPassword(modelUser.getPassword());
		return entityUser;
	}

	/**
	 * Converts an entity feed to a model feed.
	 *
	 * @param entityFeed entity feed to convert to a model feed.
	 * @return the model feed.
	 */
	public ro.unitbv.news.model.Feed toModelFeed(ro.unitbv.news.entity.Feed entityFeed) {
		ro.unitbv.news.model.Feed modelFeed = new ro.unitbv.news.model.Feed();
		modelFeed.setId(entityFeed.getId());
		modelFeed.setName(entityFeed.getName());
		modelFeed.setUrl(entityFeed.getUrl());
		modelFeed.setDescription(entityFeed.getDescription());
		modelFeed.setOwnerId(entityFeed.getOwner().getId());
		return modelFeed;
	}

	/**
	 * Converts a model feed to an entity feed.
	 *
	 * @param modelFeed model feed to convert to an entity feed.
	 * @return the entity feed.
	 */
	public ro.unitbv.news.entity.Feed toEntityFeed(ro.unitbv.news.model.Feed modelFeed) {
		ro.unitbv.news.entity.Feed entityFeed = new ro.unitbv.news.entity.Feed();
		entityFeed.setId(modelFeed.getId());
		entityFeed.setName(modelFeed.getName());
		entityFeed.setUrl(modelFeed.getUrl());
		entityFeed.setDescription(modelFeed.getDescription());
		return entityFeed;
	}
}
