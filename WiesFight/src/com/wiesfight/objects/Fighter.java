package com.wiesfight.objects;

import java.util.ArrayList;
import java.util.List;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.dto.enums.CharacterClass;

import com.wiesfight.enums.Items;
import com.wiesfight.figth.Bonus;
import com.wiesfight.figth.Bonuses;

public class Fighter implements IFighter {
	private User user;
	private double health;
	private double maxHealth;
	private Bonus bonus = new Bonus();

	public Fighter(User user) {
		this.user = user;
		this.health = this.user.getUserClass().getHealthPoints();
		this.maxHealth = this.health;
	}

	@Override
	public CharacterClass getUserClass() {
		return this.user.getUserClass();
	}

	@Override
	public double getHealth() {
		return this.health;
	}

	@Override
	public void decreaseHealth(int minus) {
        minus -= bonus.applyBonusEffect(Bonuses.DEFENCE);
        if (minus < 0) {
            minus = 0;
        }
		
		this.health -= minus;
	}

	@Override
	public int getAttackStrength() {
		double attackStrength = this.user.getUserClass().getAttackPower();
		
		attackStrength += this.bonus.applyBonusEffect(Bonuses.ATTACKPOWER);

        // TODO losowanie czy były obrażenia krytyczne czy nie
        //if (this.bonus.applyBonusEffect(Bonuses.CRITICALCHANCE)) {
        //    attackStrength += this.bonus.applyBonusEffect(Bonuses.CRITICALPOWER);
        //}

        return (int) attackStrength;	// TODO zmienić wszystko na double
	}

	@Override
	public double getMaxHealth() {
		return maxHealth;
	}
	
	@Override
	public String getName() {
		return this.user.getUserName();
	}

	@Override
	public void useAttackItem() {
		if ((this.user.getAttackItemCount() > 0) && (this.bonus.isSpecificBonusTypeEffectActive(Bonuses.ATTACKPOWER) == false)) {
			Items item = Items.values()[this.user.getUserClass().getAttackItemID()];
			this.bonus.addItem(item);
			this.user.setAttackItemCount(this.user.getAttackItemCount() - 1);
			
			// TODO animacja albo coś
		}
	}

	@Override
	public void useDefenseItem() {
		if ((this.user.getDefenseItemCount() > 0) && (this.bonus.isSpecificBonusTypeEffectActive(Bonuses.DEFENCE) == false)) {
			Items item = Items.values()[this.user.getUserClass().getDefenceItemID()];
            this.bonus.addItem(item);
			this.user.setDefenceItemCount(this.user.getDefenseItemCount() - 1);
			
			// TODO animacja albo coś
		}
	}

	@Override
	public void useMiscItem() {
		if (this.user.getMiscItemCount() > 0) {
            Items item = Items.values()[this.user.getUserClass().getMiscItemID()];

            if (item.getBonusType().equals(Bonuses.HEALTHPOINTS)) {
                this.health += item.getBonus();
            } else {
                this.bonus.addItem(item);
            }

			this.user.setMiscItemCount(this.user.getMiscItemCount() - 1);

			// TODO animacja czy coś
		}
	}

	@Override
	public int getAttackItemCount() {
		return this.user.getAttackItemCount();
	}

	@Override
	public int getDefenseItemCount() {
		return this.user.getDefenseItemCount();
	}

	@Override
	public int getMiscItemCount() {
		return this.user.getMiscItemCount();
	}

	@Override
	public int getAttackItemDuration() {
		return bonus.getDuration(Bonuses.ATTACKPOWER);
	}

	@Override
	public int getDefenseItemDuration() {
		return bonus.getDuration(Bonuses.DEFENCE);
	}
}
