import { Hero } from "@/components/Hero";
import { SearchForm } from "@/components/SearchForm";
import { HowItWorks } from "@/components/HowItWorks";

const Index = () => {
  return (
    <div className="min-h-screen">
      <Hero />
      <SearchForm />
      <HowItWorks />
    </div>
  );
};

export default Index;
