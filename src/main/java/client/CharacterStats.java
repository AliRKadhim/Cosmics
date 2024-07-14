package client;

public class CharacterStats {
    private int str;
    private int dex;
    private int int_;
    private int luk;
    private int remainingAp;

    private CharacterStats(Builder builder) {
        this.str = builder.str;
        this.dex = builder.dex;
        this.int_ = builder.int_;
        this.luk = builder.luk;
        this.remainingAp = builder.remainingAp;
    }

    public static class Builder {
        private int str;
        private int dex;
        private int int_;
        private int luk;
        private int remainingAp;

        public Builder setStr(int str) {
            this.str = str;
            return this;
        }

        public Builder setDex(int dex) {
            this.dex = dex;
            return this;
        }

        public Builder setInt(int int_) {
            this.int_ = int_;
            return this;
        }

        public Builder setLuk(int luk) {
            this.luk = luk;
            return this;
        }

        public Builder setRemainingAp(int remainingAp) {
            this.remainingAp = remainingAp;
            return this;
        }

        public CharacterStats build() {
            return new CharacterStats(this);
        }
    }

    public int getStr() {
        return str;
    }

    public int getDex() {
        return dex;
    }

    public int getInt_() {
        return int_;
    }

    public int getLuk() {
        return luk;
    }

    public int getRemainingAp() {
        return remainingAp;
    }
}
