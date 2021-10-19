interface DecodedToken {
  sub: string;
  email: string;
  isEnabled: boolean;
  authorities: string[];
}

export default DecodedToken;
