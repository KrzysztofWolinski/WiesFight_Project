package test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.dto.enums.CharacterClass;
import main.com.wiesfight.persistence.UserPersistence;

import org.junit.Test;

public class UserPersistenceTest {

	@Test
	public void shouldSaveAndLoadUserProperly() {
		User user = new User();
		UserPersistence userPersistence = new UserPersistence(user, "installation");
		
		user.setUserName("SampleName");
		user.setUserClass(CharacterClass.INFORMATYK);
		user.setUserCoins(100);
		user.setUserExperience(0);
		user.setUserLevel(1);
		user.setAttackItemCount(1);
		user.setDefenceItemCount(2);
		user.setMiscItemCount(3);
		
		userPersistence.saveUserToDB();
		
		User retreivedUser = userPersistence.loadUserFromDB();
		
		assertThat("User not retreived", user, notNullValue());
		assertThat("Names should match", user.getUserName(), equalTo(retreivedUser.getUserName()));
		assertThat("Classes should match", user.getUserClass(), equalTo(retreivedUser.getUserClass()));
		assertThat("Coins number should match", user.getUserCoins(), equalTo(retreivedUser.getUserCoins()));
		assertThat("Experience should match", user.getUserExperience(), equalTo(retreivedUser.getUserExperience()));
		assertThat("Levels should match", user.getUserLevel(), equalTo(retreivedUser.getUserLevel()));
		assertThat("Attack items count should match", user.getAttackItemCount(), equalTo(retreivedUser.getAttackItemCount()));
		assertThat("Defense items count should match", user.getDefenceItemCount(), equalTo(retreivedUser.getDefenceItemCount()));
		assertThat("Misc items count should match", user.getMiscItemCount(), equalTo(retreivedUser.getMiscItemCount()));
	}
	
}
