import axiosClient from "@/utils/axiosClient";

export async function updateAdvisorService({studentId, advisorId}: any){
  return await axiosClient.put("/admin/users/updateAdvisor", {studentId, advisorId});
}