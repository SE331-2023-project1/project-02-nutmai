import type { CommentModel } from "@/models/commentModel";
import { getTeacherById } from "@/services/teacherService";
import { defineStore } from "pinia";

export const useCommentStore = defineStore("comment", {
  state: () => ({
    comments: [] as CommentModel[],
  }),
  actions: {
    addComment(comment: CommentModel) {
      this.comments.push(comment);
    },
  },
  getters: {
    getCommentsByStudentId: (state) => {
      return (studentId: string) => state.comments.filter((comment) => comment.user.id === studentId);
    },
  },
});
