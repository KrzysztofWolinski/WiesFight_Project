package com.wiesfight.figth;

import com.wiesfight.enums.Items;

import java.util.ArrayList;

public class Bonus {
	private ArrayList<ItemWithDuration> items = new ArrayList<ItemWithDuration>();

    public void addItem(Items item) {
        items.add(new ItemWithDuration(item));
    }

    public double applyBonusEffect(Bonuses bonusType) {
        double bonusEffect = 0;

        for (ItemWithDuration item : items) {
            if ((item.isActive() && (item.getBonusType().equals(bonusType)))) {
                bonusEffect += item.getBonus();
            }
        }

        removeInactiveItems();

        return bonusEffect;
    }

    public int getDuration(Bonuses bonusType) {
        for (ItemWithDuration item : items) {
            if (item.getBonusType().equals(bonusType)) {
                return item.getDuration();
            }
        }
        return 0;
    }

    public boolean isSpecificBonusTypeEffectActive(Bonuses bonusType) {
        for (ItemWithDuration item : items) {
            if ((item.getBonusType().equals(bonusType)) && (item.isActive())) {
                return true;
            }
        }
        return false;
    }

    private void removeInactiveItems() {
        ArrayList<ItemWithDuration> temporaryList = new ArrayList<ItemWithDuration>();

        for (ItemWithDuration item : this.items) {
            if (item.isActive()) {
                temporaryList.add(item);
            }
        }

        this.items = temporaryList;
    }

    private class ItemWithDuration {
        Items item;
        int duration;

        ItemWithDuration(Items item) {
            this.item = item;
            this.duration = item.getDuration();
        }

        Items getItem() {
            return item;
        }

        Bonuses getBonusType() {
            return item.getBonusType();
        }

        double getBonus() {
            return this.item.getBonus();
        }

        int getDuration() {
            return duration;
        }

        void decrementDuration() {
            if (duration > 0) {
                duration--;
            }
        }

        boolean isActive() {
            return this.duration > 0 ? true : false;
        }
    }
	
}
