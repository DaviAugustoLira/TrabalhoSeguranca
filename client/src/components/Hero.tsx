import { Shield, AlertTriangle, CheckCircle2 } from "lucide-react";
// import heroImage from "@/assets/hero-security.jpg";

export const Hero = () => {
  return (
    <section className="relative min-h-[90vh] flex items-center justify-center overflow-hidden">
      
      <div className="absolute inset-0 bg-gradient-to-b from-transparent via-background/50 to-background" />
      
      <div className="container relative z-10 px-4 py-20">
        <div className="max-w-4xl mx-auto text-center space-y-8">
          <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-primary/10 border border-primary/20 backdrop-blur-sm">
            <Shield className="w-4 h-4 text-primary" />
            <span className="text-sm font-medium text-primary">Utilizada Have I Been Pwned</span>
          </div>
          
          <h1 className="text-5xl md:text-7xl font-bold tracking-tight">
            Suas Credenciais
              Estão Seguras?
          </h1>
          
          <p className="text-xl md:text-2xl text-muted-foreground max-w-2xl mx-auto">
            Verifique se seu email ou senha foi comprometido em vazamentos de dados conhecidos.
            Proteja sua identidade digital agora.
          </p>

        </div>
      </div>
    </section>
  );
};
