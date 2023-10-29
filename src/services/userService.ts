import type { UserModel } from "@/models/userModel";
import axiosClient from "@/utils/axiosClient";

export async function registerStudentService(student: UserModel){
  return await axiosClient.post("/auth/register", student).then(res => res.data);
}

export async function loginService(username: string, password: string){
  return await axiosClient.post("/auth/login", {username, password}).then(res => res.data);
}

export async function updateUserService(user: any){
  return await axiosClient.put("/auth/update", user).then(res => res.data);
}

export async function getAllFilesService(){
  return await axiosClient.get<{
    id: string,
    name: string,
    description: string,
    type: string,
    data: any
    createdBy: UserModel
  }[]>
  ("/files/getAllFiles").then(res => res.data);
}

export async function uploadFileService(file: {
  id: string,
  name: string,
  description: string,
  type: string,
  data: any
  createdByUserId: string
}){
  return await axiosClient.post("/teacher/files/uploadFile", file).then(res => res);
}