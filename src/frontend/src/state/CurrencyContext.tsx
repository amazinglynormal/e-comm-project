import { createContext, useContext, useState } from "react";
import Currency from "../enums/Currency.enum";

interface ProviderProps {
  children: JSX.Element | JSX.Element[];
}

const CurrencyContext = createContext({
  currency: Currency.EUR,
  changeCurrency: (currency: Currency) => {
    console.log(currency);
  },
});

const useCurrency = () => {
  const { currency, changeCurrency } = useContext(CurrencyContext);

  const handleCurrency = (value: Currency) => {
    changeCurrency(value);
  };

  return { currency, onChange: handleCurrency };
};

const CurrencyProvider = ({ children }: ProviderProps) => {
  const [currency, setCurrency] = useState(Currency.EUR);

  return (
    <CurrencyContext.Provider value={{ currency, changeCurrency: setCurrency }}>
      {children}
    </CurrencyContext.Provider>
  );
};

export { CurrencyProvider, useCurrency };
