import axiosClient from "@/utils/axiosClient";

export async function updateAdvisorService({studentId, advisorId}: any){
  return await axiosClient.put("/admin/users/updateAdvisor", {studentId, advisorId});
}

export async function createUserService(user: any){
  return await axiosClient.post("/admin/users/create", user);
}