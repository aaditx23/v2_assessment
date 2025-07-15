package com.aaditx23.v2_assessment.ui.components.DropDown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.ui.components.HSpace


@Composable
fun ExpandingDropDown(
    label: String,
    onSelected: (DropDownItem) -> Unit,
    list: List<DropDownItem>,
    modifier: Modifier = Modifier,
    selected: String = "",
    enabled: Boolean = true,
    shadow: Int = 8,
    roundedCorner: Int = 8
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(DropDownItem(selected)) }
    val interactionSource = remember { MutableInteractionSource() }
    val rotation by animateFloatAsState(if (expanded) 0f else -90f)

    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.W700
            )
        }
        Surface(
            shape = RoundedCornerShape(roundedCorner.dp),
            shadowElevation = shadow.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(roundedCorner.dp))
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            enabled = enabled
                        ) {
                            expanded = !expanded
                        }
                        .padding(vertical = 12.dp)
                        .padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(rotation)
                            .size(24.dp)
                    )
                    HSpace(5)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = selected,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
                HorizontalDivider(
                    thickness = 0.5.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                AnimatedVisibility(visible = expanded) {
                    Column(
                        modifier = Modifier
                            .heightIn(max = 300.dp)
                    ) {
                        list.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSelected(item)
                                        selectedItem = item
                                        expanded = false
                                    }
                                    .padding(horizontal = 5.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {


                                Text(
                                    text = item.name,
                                    modifier = Modifier
                                        .padding(start = 28.dp)
                                )
                                if (item.name == selected && selected.isNotBlank()) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            if (item != list.last()) {
                                HorizontalDivider(
                                    thickness = 0.8.dp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}