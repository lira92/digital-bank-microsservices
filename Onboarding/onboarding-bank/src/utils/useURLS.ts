type useURLSProps = "ACCOUNT_MENAGEMENT_URL";

/**
 * Suportado: ACCOUNT_MENAGEMENT_URL.
 * @param props 
 * @returns 
 */
export default function useURLS(props: useURLSProps) {
  const URLS = {
    ACCOUNT_MENAGEMENT_URL: "http://localhost:3000",
  };
  return URLS[props];
}
