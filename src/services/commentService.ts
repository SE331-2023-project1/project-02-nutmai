import type { CommentModel } from "@/models/commentModel";
import axiosClient from "@/utils/axiosClient";

export async function addCommentService(comment: any){
  return await axiosClient.post("/teacher/comments/create", comment).then(res => res.data);
}