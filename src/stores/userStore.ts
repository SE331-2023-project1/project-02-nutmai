import { defineStore } from "pinia";
import type { UserModel } from "@/models/userModel";

export const useUserStore = defineStore("user", {
  state: () => ({
    currentUser: {} as UserModel,
  }),
  actions: {
    setCurrentUser(user: UserModel) {
      this.currentUser = user;
    },
  },
  getters: {
    getCurrentUser(state) {
      return state.currentUser;
    },
  },
});
