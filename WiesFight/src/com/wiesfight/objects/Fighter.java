package com.wiesfight.objects;

import java.util.ArrayList;
import java.util.List;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.dto.enums.CharacterClass;

import com.wiesfight.enums.Items;
import com.wiesfight.figth.Bonus;

public class Fighter implements IFighter {
	private User user;
	private int health;
	private int maxHealth;
	private Bonus activeAttackEffect = null;
	private Bonus activeDefenseEffect = null;
	
	public Fighter(User u) {
		this.user = u;
		this.health = this.user.getUserClass().getHealthPoints();
		this.maxHealth = this.health;
	}

	@Override
	public CharacterClass getUserClass() {
		return this.user.getUserClass();
	}

	@Override
	public int getHealth() {
		return this.health;
	}

	@Override
	public void decreaseHealth(int minus) {
		if (this.activeDefenseEffect != null) {
			minus -= activeDefenseEffect.getDefenseModificator();
			if (minus < 0) {
				minus = 0;
			}
			
			if (!this.activeDefenseEffect.isActive()) {
				this.activeDefenseEffect = null;
			}
		}
		
		this.health -= minus;
	}

	@Override
	public int getAttackStrength() {
		double attackStrength = this.user.getUserClass().getAttackPower();
		
		if (this.activeAttackEffect != null) {
			attackStrength += this.activeAttackEffect.getAttackModificator();
		
			if (!this.activeAttackEffect.isActive()) {
				this.activeAttackEffect = null;
			}
		}
		
		return (int) attackStrength;	// TODO zmienić wszystko na double
	}

	@Override
	public int getMaxHealth() {
		return maxHealth;
	}
	
	@Override
	public String getName() {
		return this.user.getUserName();
	}

	@Override
	public void useAttackItem() {
		if ((this.user.getAttackItemCount() > 0) && (this.activeAttackEffect == null)) {
			Items item = Items.values()[this.user.getUserClass().getAttackItemID()];
			this.activeAttackEffect = new Bonus(item);
			this.user.setAttackItemCount(this.user.getAttackItemCount() - 1);
			
			// TODO animacja albo coś
		}
	}

	@Override
	public void useDefenseItem() {
		if ((this.user.getDefenseItemCount() > 0) && (this.activeDefenseEffect == null)) {
			Items item = Items.values()[this.user.getUserClass().getDefenceItemID()];
			this.activeDefenseEffect = new Bonus(item);
			this.user.setDefenceItemCount(this.user.getDefenseItemCount() - 1);
			
			// TODO animacja albo coś
		}
	}

	@Override
	public void useMiscItem() {
		if (this.user.getMiscItemCount() > 0) {
			Items item = Items.values()[this.user.getUserClass().getMiscItemID()];
			Bonus healingEffect = new Bonus(item);
			this.health += healingEffect.heal();
			// TODO uogólnić, teraz będzie działać tylko dla leczenia
		}
	}
}
