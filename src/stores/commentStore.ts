import type { CommentModel } from "@/models/commentModel";
import { defineStore } from "pinia";

export const useCommentStore = defineStore("comment", {
  state: () => ({
    comments: [] as CommentModel[],
  }),
  actions: {
    addComment(comment: CommentModel) {
      this.comments.push(comment);
    },
    getComments(studentID?: string) {
      if (!studentID) return [] as CommentModel[];
      this.comments = this.comments.filter((comment) => comment.studentID === studentID);
      return this.comments;
    },
  },
});
