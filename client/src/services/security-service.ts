import { api } from "../lib/axios";
import type { EmailResponse, IResponse } from "@/commons/types";

const baseURL = "/api";

export const SecurityService = {
  checkPassword: async (
    password: string
  ): Promise<IResponse<EmailResponse>> => {
    try {
      const response = await api.post(`${baseURL}/check`, {
        password,
      });

      return {
        status: 200,
        success: true,
        message: response.data?.message ?? "Verificação realizada!",
        data: response.data,
      };
    } catch (err: any) {
      return {
        status: err.response?.status || 500,
        success: false,
        message: "Falha ao verificar senha.",
        data: err.response?.data,
      };
    }
  },

  checkEmail: async (email: string): Promise<IResponse<any>> => {
    try {
      const response = await api.post(`${baseURL}/email`, {
        email,
      });

      return {
        status: 200,
        success: true,
        message: "Verificação concluída!",
        data: response.data,
      };
    } catch (err: any) {
      return {
        status: err.response?.status || 500,
        success: false,
        message: "Falha ao verificar e-mail.",
        data: err.response?.data,
      };
    }
  },
};