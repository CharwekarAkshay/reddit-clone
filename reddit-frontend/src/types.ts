export type SignUpRequestPayload = {
  username: string;
  password: string;
  email: string;
};

export type LoginRequestPayload = {
  username: string,
  password: string,
};


export type LoginResponsePayload = {
  authenticationToken: string,
  refreshToken: string,
  expiresAt: Date,
  username: string,
};