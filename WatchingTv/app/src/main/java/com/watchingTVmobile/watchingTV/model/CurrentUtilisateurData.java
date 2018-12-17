package com.watchingTVmobile.watchingTV.model;

import com.google.gson.annotations.SerializedName;

public class CurrentUtilisateurData {

    @SerializedName("utilisateur")
    private Utilisateur mUtilisateur;

    public Utilisateur getmUtilisateur() {
        return mUtilisateur;
    }

    public void setmUtilisateur(Utilisateur mUtilisateur) {
        this.mUtilisateur = mUtilisateur;
    }
}
