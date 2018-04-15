//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.10 at 02:35:07 PM CET 
//


package bpsim;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DistributionParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DistributionParameter">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.bpsim.org/schemas/2.0}ParameterValue">
 *       &lt;attribute name="timeUnit" type="{http://www.bpsim.org/schemas/2.0}TimeUnit" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DistributionParameter")
@XmlSeeAlso({
    PoissonDistribution.class,
    UserDistribution.class,
    ErlangDistribution.class,
    TruncatedNormalDistribution.class,
    UniformDistribution.class,
    LogNormalDistribution.class,
    BetaDistribution.class,
    NegativeExponentialDistribution.class,
    NormalDistribution.class,
    WeibullDistribution.class,
    BinomialDistribution.class,
    TriangularDistribution.class,
    GammaDistribution.class
})
public class DistributionParameter
    extends ParameterValue
{

    @XmlAttribute(name = "timeUnit")
    protected TimeUnit timeUnit;

    /**
     * Gets the value of the timeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link TimeUnit }
     *     
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /**
     * Sets the value of the timeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeUnit }
     *     
     */
    public void setTimeUnit(TimeUnit value) {
        this.timeUnit = value;
    }

}