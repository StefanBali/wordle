import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "de.lise.letscode", importOptions = DoNotIncludeTests.class)
public class DependencyDirectionTest {
    private static final String DOMAIN_LAYER = "de.lise.letscode.*.usecase..";
    private static final String REPOSITORY_LAYER = "de.lise.letscode.*.adapter..";
    private static final String CONTROLLER_LAYER = "de.lise.letscode.*.endpoint..";

    @ArchTest
    private final ArchRule domainLayerShouldNotAccessOtherLayers = classes().that().resideInAPackage(DOMAIN_LAYER)
            .should().onlyAccessClassesThat().resideInAnyPackage(DOMAIN_LAYER, "java..")
            .allowEmptyShould(true);

    @ArchTest
    private final ArchRule adapterLayerShouldOnlyAccessDomain = classes().that().resideInAPackage(REPOSITORY_LAYER)
            .should().onlyAccessClassesThat().resideInAnyPackage(REPOSITORY_LAYER, DOMAIN_LAYER, "java..", "javax..", "org.bson..", "com.mongodb..")
            .allowEmptyShould(true);

    @ArchTest
    private final ArchRule endpointLayerShouldOnlyAccessDomain = classes().that().resideInAPackage(CONTROLLER_LAYER)
            .should().onlyAccessClassesThat().resideInAnyPackage(CONTROLLER_LAYER, DOMAIN_LAYER,
                    "java..", "javax..", "org.bson..", "com.mongodb..", "io.vertx..", "org.jboss.resteasy..")
            .allowEmptyShould(true);
}
