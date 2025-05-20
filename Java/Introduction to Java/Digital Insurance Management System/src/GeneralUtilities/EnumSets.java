
package GeneralUtilities;

import java.util.*;

/**
 * @author brand
 */

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
      
      private final int value;
      
      SumInsured(int value) {
          this.value = value;
      }
      
      public int getValue() {
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
    
    public EnumSet<PolicyType> policyTypeSet() {
        
        EnumSet<PolicyType> policySet = EnumSet.allOf(PolicyType.class);
        return policySet;
        
    }
    
    public EnumSet<SumInsured> sumInsuredSet() {
        
        EnumSet<SumInsured> sumInsuredSet = EnumSet.allOf(SumInsured.class);
        return sumInsuredSet;
        
    }
    
    public EnumSet<PolicyPremium> policyPremiumSet() {
        
        EnumSet<PolicyPremium> policyPremiumSet = EnumSet.allOf(PolicyPremium.class);
        return policyPremiumSet;
        
    }
      
}
