import type { UserModel } from "@/models/userModel";
import axiosClient from "@/utils/axiosClient";

export async function registerStudentService(student: UserModel){
  return await axiosClient.post("/auth/register", student).then(res => res.data);
}

export async function loginService(username: string, password: string){
  return await axiosClient.post("/auth/login", {username, password}).then(res => res.data);
}