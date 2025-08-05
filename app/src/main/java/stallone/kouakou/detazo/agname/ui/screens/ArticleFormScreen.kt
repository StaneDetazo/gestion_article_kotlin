package stallone.kouakou.detazo.agname.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import stallone.kouakou.detazo.agname.data.entites.Article
import stallone.kouakou.detazo.agname.viewmodel.ArticleViewModel

@ExperimentalMaterial3Api
@Composable
fun ArticleFormScreen(
    viewModel: ArticleViewModel,
    onArticleSaved: () -> Unit,
    onNavigateBack: () -> Unit = {},
    existingArticle: Article? = null
) {
    var name by remember { mutableStateOf(existingArticle?.name ?: "") }
    var category by remember { mutableStateOf(existingArticle?.category ?: "") }
    var price by remember { mutableStateOf(existingArticle?.price?.toString() ?: "") }
    var quantity by remember { mutableStateOf(existingArticle?.quantity?.toString() ?: "") }
    var description by remember { mutableStateOf(existingArticle?.description ?: "") }

    // États pour la validation
    var nameError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }
    var quantityError by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Fonction de validation
    val validateForm = {
        nameError = name.isBlank()
        priceError = price.isBlank() || price.toDoubleOrNull() == null || price.toDoubleOrNull()!! < 0
        quantityError = quantity.isBlank() || quantity.toIntOrNull() == null || quantity.toIntOrNull()!! < 0

        !nameError && !priceError && !quantityError
    }

    val isEditing = existingArticle != null
    val title = if (isEditing) "Modifier l'Article" else "Ajouter un Article"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Contenu principal du formulaire
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Titre de la section
                    Text(
                        text = "Informations de l'article",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Champ Nom
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            nameError = false
                        },
                        label = { Text("Nom de l'article *") },
                        isError = nameError,
                        supportingText = if (nameError) {
                            { Text("Le nom est requis", color = MaterialTheme.colorScheme.error) }
                        } else null,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Champ Catégorie
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Catégorie") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Rangée Prix/Quantité
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Champ Prix
                        OutlinedTextField(
                            value = price,
                            onValueChange = {
                                price = it
                                priceError = false
                            },
                            label = { Text("Prix *") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            isError = priceError,
                            supportingText = if (priceError) {
                                { Text("Prix invalide", color = MaterialTheme.colorScheme.error) }
                            } else null,
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            suffix = { Text("€", style = MaterialTheme.typography.bodyMedium) }
                        )

                        // Champ Quantité
                        OutlinedTextField(
                            value = quantity,
                            onValueChange = {
                                quantity = it
                                quantityError = false
                            },
                            label = { Text("Quantité *") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = quantityError,
                            supportingText = if (quantityError) {
                                { Text("Quantité invalide", color = MaterialTheme.colorScheme.error) }
                            } else null,
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    // Champ Description
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description (facultatif)") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )
                }
            }

            // Section Boutons
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Bouton principal (Enregistrer)
                    Button(
                        onClick = {
                            if (validateForm()) {
                                isLoading = true
                                val article = Article(
                                    id = existingArticle?.id ?: 0,
                                    name = name.trim(),
                                    category = category.trim(),
                                    price = price.toDoubleOrNull() ?: 0.0,
                                    quantity = quantity.toIntOrNull() ?: 0,
                                    description = if (description.trim().isBlank()) null else description.trim()
                                )

                                if (existingArticle == null) {
                                    viewModel.insert(article)
                                } else {
                                    viewModel.update(article)
                                }
                                onArticleSaved()
                                isLoading = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        } else {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = if (isLoading) "Enregistrement..."
                            else if (isEditing) "Mettre à jour"
                            else "Ajouter l'article"
                        )
                    }

                    // Bouton de suppression (seulement en mode édition)
                    if (isEditing) {
                        OutlinedButton(
                            onClick = { showDeleteDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Supprimer l'article")
                        }
                    }

                    // Note sur les champs requis
                    Text(
                        text = "* Champs obligatoires",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Espaceur pour éviter que le contenu soit masqué
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Dialog de confirmation de suppression
        if (showDeleteDialog && existingArticle != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                icon = {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                title = {
                    Text(
                        "Confirmer la suppression",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                text = {
                    Text(
                        "Êtes-vous sûr de vouloir supprimer \"${existingArticle.name}\" ? Cette action est irréversible.",
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.delete(existingArticle)
                            showDeleteDialog = false
                            onArticleSaved()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Supprimer")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Annuler")
                    }
                }
            )
        }
    }
}