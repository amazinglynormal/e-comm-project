import { useState } from "react";

import { MobileMenu } from "./MobileMenu";
import { DesktopNavigation } from "./DesktopNavigation";
import { TopNavigation } from "./TopNavigation";

export const StoreNavigation = () => {
  const [open, setOpen] = useState(false);

  return (
    <div className="bg-white">
      <MobileMenu open={open} setOpen={setOpen} />
      <header className="relative">
        <nav aria-label="Top">
          <TopNavigation />
          <DesktopNavigation setOpen={setOpen} />
        </nav>
      </header>
    </div>
  );
};
