import type { CommentModel } from "@/models/commentModel";
import axiosClient from "@/utils/axiosClient";

export async function addCommentService(comment: any){
  return await axiosClient.post("/teacher/comments/create", comment).then(res => res.data);
}

export async function addReplyService(reply: any){
  return await axiosClient.post("/student/replies/create", reply).then(res => res.data);
}

export async function getCommentService(id: string){
  return await axiosClient.get(`/comments/get?id=${id}`).then(res => res.data);
}

export async function getAllRepliesByCommentIdService(id: string){
  return await axiosClient.get(`/replies/get?id=${id}`).then(res => res.data);
}