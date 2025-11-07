import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Card } from "@/components/ui/card";
import { Mail, Lock, Search, Loader2, Shield } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

export const SearchForm = () => {
  const [emailValue, setEmailValue] = useState("");
  const [passwordValue, setPasswordValue] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const { toast } = useToast();

  const handleEmailSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    
    // Simulate API call
    setTimeout(() => {
      setIsLoading(false);
      toast({
        title: "Busca Concluída",
        description: `Verificando vazamentos para: ${emailValue}`,
      });
    }, 2000);
  };

  const handlePasswordSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    
    // Simulate API call
    setTimeout(() => {
      setIsLoading(false);
      toast({
        title: "Busca Concluída",
        description: "Senha verificada com segurança.",
      });
    }, 2000);
  };

  return (
    <section className="py-20 px-4">
      <div className="container max-w-2xl mx-auto">
        <Card className="p-6 md:p-8 bg-card border-border shadow-[var(--shadow-elevated)]">
          <Tabs defaultValue="email" className="w-full">
            <TabsList className="grid w-full grid-cols-2 mb-8">
              <TabsTrigger value="email" className="gap-2">
                <Mail className="w-4 h-4" />
                Email
              </TabsTrigger>
              <TabsTrigger value="password" className="gap-2">
                <Lock className="w-4 h-4" />
                Senha
              </TabsTrigger>
            </TabsList>
            
            <TabsContent value="email" className="space-y-4">
              <div className="space-y-2">
                <h3 className="text-2xl font-bold">Verificar Email</h3>
              </div>
              
              <form onSubmit={handleEmailSearch} className="space-y-4">
                <div className="relative">
                  <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                  <Input
                    type="email"
                    placeholder="seu@email.com"
                    value={emailValue}
                    onChange={(e) => setEmailValue(e.target.value)}
                    className="pl-10 h-12 bg-secondary/50 border-border"
                    required
                  />
                </div>
                
                <Button 
                  type="submit" 
                  className="w-full h-12 bg-primary hover:bg-primary/90 text-primary-foreground font-semibold glow-cyan"
                  disabled={isLoading}
                >
                  {isLoading ? (
                    <>
                      <Loader2 className="mr-2 h-5 w-5 animate-spin" />
                      Verificando...
                    </>
                  ) : (
                    <>
                      <Search className="mr-2 h-5 w-5" />
                      Verificar Email
                    </>
                  )}
                </Button>
              </form>
            </TabsContent>
            
            <TabsContent value="password" className="space-y-4">
              <div className="space-y-2">
                <h3 className="text-2xl font-bold">Verificar Senha</h3>
              </div>
              
              <form onSubmit={handlePasswordSearch} className="space-y-4">
                <div className="relative">
                  <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                  <Input
                    type="password"
                    placeholder="Digite sua senha"
                    value={passwordValue}
                    onChange={(e) => setPasswordValue(e.target.value)}
                    className="pl-10 h-12 bg-secondary/50 border-border"
                    required
                  />
                </div>
                
                <div className="p-3 rounded-lg bg-muted/50 border border-border">
                  <p className="text-sm text-muted-foreground flex items-start gap-2">
                    <Shield className="w-4 h-4 mt-0.5 text-success flex-shrink-0" />
                    <span>
                      Sua senha nunca é enviada diretamente. Usamos k-anonymity para verificar com segurança.
                    </span>
                  </p>
                </div>
                
                <Button 
                  type="submit" 
                  className="w-full h-12 bg-primary hover:bg-primary/90 text-primary-foreground font-semibold glow-cyan"
                  disabled={isLoading}
                >
                  {isLoading ? (
                    <>
                      <Loader2 className="mr-2 h-5 w-5 animate-spin" />
                      Verificando...
                    </>
                  ) : (
                    <>
                      <Search className="mr-2 h-5 w-5" />
                      Verificar Senha
                    </>
                  )}
                </Button>
              </form>
            </TabsContent>
          </Tabs>
        </Card>
      </div>
    </section>
  );
};
