package main.com.wiesfight.persistence;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.dto.enums.CharacterClass;
import main.com.wiesfight.persistence.enums.UserParametersEnum;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

@ParseClassName("Users")
public class UserPersistence extends ParseObject {
	
	private User user;
	
	public UserPersistence() {
	}
	
	public UserPersistence(User user, String installation) {
		this.setUser(user);		
		this.put("Installation", installation);
	}
		
	public Boolean saveUserToDB() {
		this.put(UserParametersEnum.USER_NAME.getParameterName(), this.user.getUserName());
		this.put(UserParametersEnum.USER_CLASS.getParameterName(), this.user.getUserClass().ordinal());
		this.put(UserParametersEnum.LEVEL.getParameterName(), this.user.getUserLevel());
		this.put(UserParametersEnum.EXPERIENCE.getParameterName(), this.user.getUserExperience());
		this.put(UserParametersEnum.COINS.getParameterName(), this.user.getUserCoins());
		this.put(UserParametersEnum.ATTACK_ITEMS_COUNT.getParameterName(), this.user.getAttackItemCount());
		this.put(UserParametersEnum.DEFENSE_ITEMS_COUNT.getParameterName(), this.user.getDefenseItemCount());
		this.put(UserParametersEnum.MISC_ITEMS_COUNT.getParameterName(), this.user.getMiscItemCount());
		this.put(UserParametersEnum.LAST_BONUS_DATE.getParameterName(), this.user.getLastBonusDate());
		this.put(UserParametersEnum.FIGHTS.getParameterName(), this.user.getFights());
		this.put(UserParametersEnum.WINS.getParameterName(), this.user.getWins());
		
		try {
			this.save();
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	public User loadUserFromDB() {
		this.user = new User();
		this.user.setUserName(this.getString(UserParametersEnum.USER_NAME.getParameterName()));
		this.user.setUserClass(CharacterClass.values()[this.getInt(UserParametersEnum.USER_CLASS.getParameterName())]);
		this.user.setUserLevel(this.getInt(UserParametersEnum.LEVEL.getParameterName()));
		this.user.setUserExperience(this.getInt(UserParametersEnum.EXPERIENCE.getParameterName()));
		this.user.setUserCoins(this.getInt(UserParametersEnum.COINS.getParameterName()));
		this.user.setAttackItemCount(this.getInt(UserParametersEnum.ATTACK_ITEMS_COUNT.getParameterName()));
		this.user.setDefenceItemCount(this.getInt(UserParametersEnum.DEFENSE_ITEMS_COUNT.getParameterName()));
		this.user.setMiscItemCount(this.getInt(UserParametersEnum.MISC_ITEMS_COUNT.getParameterName()));
		this.user.setFights(this.getInt(UserParametersEnum.FIGHTS.getParameterName()));
		this.user.setWins(this.getInt(UserParametersEnum.WINS.getParameterName()));
		this.user.setLastBonusDate(this.getDate(UserParametersEnum.LAST_BONUS_DATE.getParameterName()));
		
		return user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void removeFromDB() {
		this.deleteInBackground();
	}
}
