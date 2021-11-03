import { createContext, useContext, useState } from "react";

interface ProviderProps {
  children: JSX.Element | JSX.Element[];
}

const CURRENCIES = {
  Eur: "EUR",
  Gbp: "GBP",
  Usd: "USD",
};

const CurrencyContext = createContext({
  currency: CURRENCIES.Eur,
  changeCurrency: (currency: string) => {
    console.log(currency);
  },
});

const useCurrency = () => {
  const { currency, changeCurrency } = useContext(CurrencyContext);

  const handleCurrency = (value: string) => {
    changeCurrency(value);
  };

  return { currency, onChange: handleCurrency };
};

const CurrencyProvider = ({ children }: ProviderProps) => {
  const [currency, setCurrency] = useState(CURRENCIES.Eur);

  return (
    <CurrencyContext.Provider value={{ currency, changeCurrency: setCurrency }}>
      {children}
    </CurrencyContext.Provider>
  );
};

export { CurrencyProvider, CURRENCIES, useCurrency };
