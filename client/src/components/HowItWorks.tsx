import { Shield, Database, CheckCircle, Lock } from "lucide-react";
import { Card } from "@/components/ui/card";

export const HowItWorks = () => {
  const steps = [
    {
      icon: Database,
      title: "Dados de Vazamentos",
      description: "Integramos com Have I Been Pwned, que rastreia bilhões de credenciais comprometidas de vazamentos públicos.",
    },
    {
      icon: Lock,
      title: "Busca Anônima",
      description: "Suas credenciais são verificadas de forma segura usando técnicas de hash e k-anonymity para total privacidade.",
    },
    {
      icon: CheckCircle,
      title: "Resultado Instantâneo",
      description: "Receba imediatamente informações sobre se suas credenciais foram expostas e em quais vazamentos.",
    },
    {
      icon: Shield,
      title: "Ação Preventiva",
      description: "Se houver comprometimento, receba orientações imediatas sobre como proteger suas contas.",
    },
  ];

  return (
    <section className="py-20 px-4 bg-gradient-to-b from-background to-secondary/20">
      <div className="container max-w-6xl mx-auto">
        <div className="text-center mb-16 space-y-4">
          <h2 className="text-4xl md:text-5xl font-bold">Como Funciona</h2>
          <p className="text-xl text-muted-foreground max-w-2xl mx-auto">
            Tecnologia de ponta para proteger sua identidade digital
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {steps.map((step, index) => (
            <Card 
              key={index}
              className="p-6 bg-card border-border hover:border-primary/50 transition-all duration-300 hover:shadow-[var(--shadow-elevated)] group"
            >
              <div className="flex gap-4">
                <div className="flex-shrink-0">
                  <div className="w-12 h-12 rounded-lg bg-primary/10 border border-primary/20 flex items-center justify-center group-hover:bg-primary/20 transition-colors">
                    <step.icon className="w-6 h-6 text-primary" />
                  </div>
                </div>
                <div className="space-y-2">
                  <h3 className="text-xl font-semibold">{step.title}</h3>
                  <p className="text-muted-foreground">{step.description}</p>
                </div>
              </div>
            </Card>
          ))}
        </div>

        <div className="mt-12 p-6 rounded-lg bg-success/10 border border-success/20">
          <div className="flex items-start gap-4">
            <Shield className="w-6 h-6 text-success flex-shrink-0 mt-1" />
            <div className="space-y-2">
              <h4 className="text-lg font-semibold text-success">Seu Privacidade é Nossa Prioridade</h4>
              <p className="text-sm text-muted-foreground">
                Não armazenamos suas credenciais. Todas as verificações são processadas de forma anônima 
                e segura, garantindo que suas informações permaneçam privadas.
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
