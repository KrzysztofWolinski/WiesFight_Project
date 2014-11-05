package main.com.wiesfight.persistence;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.persistence.enums.UserParametersEnum;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Users")
public class UserPersistence extends ParseObject {
	
	private User user;
	
	public UserPersistence(User user, String installation) {
		this.setUser(user);
		put("Installation", installation);	// TODO co to jest installation?
	}
		
	public void saveUserToDB() {
		this.put(UserParametersEnum.USER_NAME.getParameterName(), this.user.getUserName());
		this.put(UserParametersEnum.USER_CLASS.getParameterName(), this.user.getUserClass());
		this.put(UserParametersEnum.LEVEL.getParameterName(), this.user.getUserLevel());
		this.put(UserParametersEnum.EXPERIENCE.getParameterName(), this.user.getUserExperience());
		this.put(UserParametersEnum.COINS.getParameterName(), this.user.getUserCoins());
		this.put(UserParametersEnum.ATTACK_ITEMS_COUNT.getParameterName(), this.user.getAttackItemCount());	// TODO wszystkie itemy jako jeden typ?
		this.put(UserParametersEnum.DEFENSE_ITEMS_COUNT.getParameterName(), this.user.getDefenceItemCount());
		this.put(UserParametersEnum.MISC_ITEMS_COUNT.getParameterName(), this.user.getMiscItemCount());
		
		this.saveInBackground();
	}
	
	public User loadUserFromDB() {
		this.user.setUserName(getString(UserParametersEnum.USER_NAME.getParameterName()));
		 this.user.setUserClass(getInt(UserParametersEnum.USER_CLASS.getParameterName()));	// TODO
		this.user.setUserLevel(getInt(UserParametersEnum.LEVEL.getParameterName()));
		this.user.setUserExperience(getInt(UserParametersEnum.EXPERIENCE.getParameterName()));
		this.user.setUserCoins(getInt(UserParametersEnum.COINS.getParameterName()));
		this.user.setAttackItemCount(getInt(UserParametersEnum.ATTACK_ITEMS_COUNT.getParameterName()));
		this.user.setDefenceItemCount(getInt(UserParametersEnum.DEFENSE_ITEMS_COUNT.getParameterName()));
		this.user.setMiscItemCount(getInt(UserParametersEnum.MISC_ITEMS_COUNT.getParameterName()));
		
		return user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
