import { defineStore } from "pinia";
import type { UserModel } from "@/models/userModel";

export const useUserStore = defineStore("user", {
  state: () => ({
  }),
  actions: {
    logout() {
      localStorage.removeItem("user");
    },
  },
  getters: {
    getCurrentUser(state) {
      return JSON.parse(localStorage.getItem("user") || "{}") as UserModel;
    },
  },
});
