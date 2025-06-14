
package GeneralUtilities;

public class EnumSets {
    
    public enum PolicyType {
        
        Life,
        Health,
        Auto
        
    };
    
    public enum SumInsured {
        
      TenThousand(10000),
      TwentyThousand(20000),
      ThirtyThousand(30000);
      
      private final double value;
      
      SumInsured(double value) {
          this.value = value;
      }
      
      public double getValue() {
          return value;
      }
      
    }
    
    public enum PolicyPremium {
        Life("Life", 0.05),
        Health("Health", 0.08),
        Auto("Auto", 0.10);
        
        private double premiumRate;
        private String policyType;
        
        PolicyPremium(String policyType, double premiumRate) {
            this.policyType = policyType;
            this.premiumRate = premiumRate;
        }
        
        public String getPolicyType() {
            return policyType;
        }
        
        public double getPremiumRate() {
            return premiumRate;
        }
        
        public static PolicyPremium fromPolicyType(String policyType) {
            
            for (PolicyPremium premium : PolicyPremium.values()) {
                if (premium.getPolicyType().equals(policyType)) {
                    return premium;
                }
            }
            
            return null;
        }
        
    }
      
}
