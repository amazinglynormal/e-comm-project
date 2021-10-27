import { AxiosResponseHeaders } from "axios";

export default function extractJwtTokenFromResponseHeaders(
  headers: AxiosResponseHeaders
) {
  return headers["authorization"].split("Bearer ")[1];
}
