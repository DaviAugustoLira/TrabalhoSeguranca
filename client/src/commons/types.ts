export interface IResponse<T = any> {
  status?: number;
  success?: boolean;
  message?: string;
  data?: T
}


export interface ResultResponse{
  Name: string
}

export interface EmailResponse{
  email: string;
  pwned: boolean;
  result: ResultResponse;
}


