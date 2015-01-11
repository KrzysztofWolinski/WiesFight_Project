package com.wiesfight.objects;

import android.util.Log;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.dto.enums.CharacterClass;

import com.wiesfight.enums.Items;
import com.wiesfight.figth.Bonus;
import com.wiesfight.figth.Bonuses;
import com.wiesfight.figth.PlayerAction;

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
		this.health -= minus;
	}

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

	@Override
	public PlayerAction getAttackStrength() {
        PlayerAction action = new PlayerAction();

        int attackChance = (int) ((this.getUserClass().getAccuracy() * 100) - (Math.random() * 100));

        Log.i("[attackChance]", String.valueOf(attackChance));

        double attackStrength = 0.0;

        if (attackChance > 0) {
            attackStrength = this.user.getUserClass().getAttackPower();

            attackStrength += this.bonus.applyBonusEffect(Bonuses.ATTACKPOWER);

            // TODO losowanie czy były obrażenia krytyczne czy nie - sprawdzić czy na pewno działa
            double criticalChance = this.getUserClass().getCriticalChance() + this.bonus.applyBonusEffect(Bonuses.CRITICALCHANCE) - Math.random();
            boolean isCriticalAttack = (criticalChance >= 0.0) ? true : false;
            action.setIsCriticalAttack(isCriticalAttack);

            Log.i("[critical]", String.valueOf(criticalChance));

            if (isCriticalAttack) {
                attackStrength += this.bonus.applyBonusEffect(Bonuses.CRITICALPOWER);
            }
        }

        action.setDamage(attackStrength);

        return action;
	}

    @Override
    public PlayerAction evaluateDamage(PlayerAction action) {
        double damage = action.getDamage();
        damage -= bonus.applyBonusEffect(Bonuses.DEFENCE);
        if (damage < 0) {
            damage = 0;
        }

        action.setDamage(damage);
        return action;
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
	public boolean useAttackItem() {
		if ((this.user.getAttackItemCount() > 0) && (this.bonus.isSpecificBonusTypeEffectActive(Bonuses.ATTACKPOWER) == false)) {
			Items item = Items.values()[this.user.getUserClass().getAttackItemID()];
			this.bonus.addItem(item);
			this.user.setAttackItemCount(this.user.getAttackItemCount() - 1);
            return true;
		}
        return false;
	}

	@Override
	public boolean useDefenseItem() {
		if ((this.user.getDefenseItemCount() > 0) && (this.bonus.isSpecificBonusTypeEffectActive(Bonuses.DEFENCE) == false)) {
			Items item = Items.values()[this.user.getUserClass().getDefenceItemID()];
            this.bonus.addItem(item);
			this.user.setDefenceItemCount(this.user.getDefenseItemCount() - 1);
            return true;
		}
        return false;
	}

	@Override
	public boolean useMiscItem() {
		if (this.user.getMiscItemCount() > 0) {
            Items item = Items.values()[this.user.getUserClass().getMiscItemID()];

            if (item.getBonusType().equals(Bonuses.HEALTHPOINTS)) {
                this.health += item.getBonus();
            } else {
                this.bonus.addItem(item);
            }

			this.user.setMiscItemCount(this.user.getMiscItemCount() - 1);

            return true;
		}
        return false;
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

    @Override
    public void endTurn() {
        this.bonus.decrementDuration();
    }
}
