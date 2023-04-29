package net.ddellspe

import com.google.inject.Inject
import com.snaplogic.api.ConfigurationException
import com.snaplogic.common.SnapType
import com.snaplogic.common.properties.SnapProperty.DecoratorType
import com.snaplogic.common.properties.builders.PropertyBuilder
import com.snaplogic.snap.api.Document
import com.snaplogic.snap.api.DocumentUtility
import com.snaplogic.snap.api.ExpressionProperty
import com.snaplogic.snap.api.OutputViews
import com.snaplogic.snap.api.PropertyValues
import com.snaplogic.snap.api.SimpleSnap
import com.snaplogic.snap.api.SnapCategory
import com.snaplogic.snap.api.capabilities.Category
import com.snaplogic.snap.api.capabilities.General
import com.snaplogic.snap.api.capabilities.Inputs
import com.snaplogic.snap.api.capabilities.Outputs
import com.snaplogic.snap.api.capabilities.Version
import com.snaplogic.snap.api.capabilities.ViewType

@General(
    title = "Kotlin Snap",
    author = "David Dellsperger",
    purpose = "Write a snap using Kotlin, because why not",
    docLink = "https://snaplogic.com"
)
@Category(snap = SnapCategory.READ)
@Version(snap = 1)
@Inputs(min = 0, max = 1, accepts = [ViewType.DOCUMENT])
@Outputs(min = 1, max = 1, offers = [ViewType.DOCUMENT])
class KotlinSnap : SimpleSnap() {
    @Inject
    private val documentUtility: DocumentUtility? = null

    @Inject
    private val outputViews: OutputViews? = null

    private var propValueExpr: ExpressionProperty? = null
    private var propKeyExpr: ExpressionProperty? = null

    override fun defineProperties(propBuilder: PropertyBuilder) {
        propBuilder.describe("propKey", "Property Key").type(SnapType.STRING).required()
            .expression(DecoratorType.ACCEPTS_SCHEMA).add()
        propBuilder.describe("propValue", "Property Value").type(SnapType.STRING).required()
            .expression(DecoratorType.ACCEPTS_SCHEMA).add()
    }

    @Throws(ConfigurationException::class)
    override fun configure(propertyValues: PropertyValues) {
        super.configure(propertyValues)
        propKeyExpr = propertyValues.getAsExpression("propKey")
        propValueExpr = propertyValues.getAsExpression("propValue")
    }

    override fun process(document: Document, viewName: String) {
        val key = propKeyExpr!!.eval<String>(document);
        val value = propValueExpr!!.eval<String>(document);
        val data: MutableMap<String, String> = java.util.LinkedHashMap()
        data[key] = value
        outputViews.write(documentUtility!!.newDocument(data))
    }
}