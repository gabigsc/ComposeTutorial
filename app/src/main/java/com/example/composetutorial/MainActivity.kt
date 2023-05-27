package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//Para uma função MessageCard se ligar ao tema do Material Design, ComposeTutorialTheme, como um Surface,
//Vai permitir que as funções de composição herdem os estilos definidos no tema do app
            setContent {
                ComposeTutorialTheme {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }

    data class Message(val author: String, val body: String)

    //Torna a função composta
    @Composable
    fun MessageCard(msg: Message) {
        //Use o Resource Manager para importar uma imagem da sua biblioteca de fotos
        //Função de composição Row para ter um design bem estruturado e uma Image dentro dela
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(R.drawable.profile_picture),
                contentDescription = null,
                modifier = Modifier
            // Define o tamanho da imagem para 40 dp
                    .size(40.dp)
            // Clipe da imagem para ter a forma de um círculo
                    .clip(CircleShape)
            //Adicionar preenchimento em torno de nossa mensagem
                    .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
            )
             //Adiciona um espaço horizontal entre a imagem e a coluna
            Spacer(modifier = Modifier.width(8.dp))

            //Mudar o plano de fundo do conteúdo da mensagem com base em isExpanded ao clicar em uma mensagem
            //Acompanhamos se a mensagem é expandida ou não nesta variável
            var isExpanded by remember { mutableStateOf(false) }
            //AnimateColorAsState cria uma animação com essa cor modificando o valor dela de MaterialTheme.colors.surface para MaterialTheme.colors.primary e vice-versa.
            //SurfaceColor será atualizado gradativamente de uma cor para outra
            val surfaceColor by animateColorAsState(
                if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
            )

            //Vai usar o modificador clickable para processar eventos de clique na função de composição
            //Alternamos a variável isExpanded quando clicamos nesta coluna
            //A função Column permite organizar os elementos verticalmente
            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text = msg.author,
            //Use MaterialTheme.colors para definir o estilo com as cores do tema envolvido
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )

            //Adiciona um espaço vertical entre o autor e os textos da mensagem
                Spacer(modifier = Modifier.height(4.dp))

            //Surface permite personalizar a forma e a elevação do corpo da mensagem
                Surface(
            //Shape para adicionar os toques finais
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
            //A cor da surfaceColor mudará gradualmente de primária para superfície
                    color = surfaceColor,
            //AnimateContentSize mudará o tamanho da superfície do contêiner da mensagem gradualmente
                    modifier = Modifier.animateContentSize().padding(1.dp)
                ) {
                    Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 4.dp),
            //Se a mensagem for expandida, exibimos o seu conteúdo
            //Senão mostramos apenas a primeira linha
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }

    //Anotação de prévia e ativação do modo noturno
//Permite visualizar as funções de composição no Android Studio sem precisar criar e instalar o app em um emulador ou dispositivo Android
    @Preview(name = "Light Mode")
    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        name = "Dark Mode"
    )
    @Composable
//Segunda função nomeada como PreviewMessageCard, que chama MessageCard com um parâmetro adequado
    fun PreviewMessageCard() {
        ComposeTutorialTheme {
            Surface {
                MessageCard(
                    msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
                )
            }
        }
    }

    @Composable
//uma função Conversation mostrará várias mensagens
    fun Conversation(messages: List<Message>) {
//LazyColumn e LazyRow do Compose processam apenas os elementos visíveis na tela e são muito eficientes para listas longas.
        LazyColumn {
//O elemento items é filho da função LazyColumn.
//Uma List é usada como parâmetro, e o lambda recebe um parâmetro que chamamos de message
//Lambda é chamado para cada item da List fornecida
            items(messages) { message ->
                MessageCard(message)
            }
        }
    }

    @Preview
    @Composable
    fun PreviewConversation() {
        ComposeTutorialTheme {
            Conversation(SampleData.conversationSample)
        }
    }

    object SampleData {
        // Sample conversation data
        val conversationSample = listOf(
            Message(
                "Colleague",
                "Test...Test...Test..."
            ),
            Message(
                "Colleague",
                "List of Android versions:\n" +
                        "Android KitKat (API 19)\n" +
                        "Android Lollipop (API 21)\n" +
                        "Android Marshmallow (API 23)\n" +
                        "Android Nougat (API 24)\n" +
                        "Android Oreo (API 26)\n" +
                        "Android Pie (API 28)\n" +
                        "Android 10 (API 29)\n" +
                        "Android 11 (API 30)\n" +
                        "Android 12 (API 31)\n"
            ),
            Message(
                "Colleague",
                "I think Kotlin is my favorite programming language.\n" +
                        "It's so much fun!"
            ),
            Message(
                "Colleague",
                "Searching for alternatives to XML layouts..."
            ),
            Message(
                "Colleague",
                "Hey, take a look at Jetpack Compose, it's great!\n" +
                        "It's the Android's modern toolkit for building native UI." +
                        "It simplifies and accelerates UI development on Android." +
                        "Less code, powerful tools, and intuitive Kotlin APIs :)"
            ),
            Message(
                "Colleague",
                "It's available from API 21+ :)"
            ),
            Message(
                "Colleague",
                "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
            ),
            Message(
                "Colleague",
                "Android Studio next version's name is Arctic Fox"
            ),
            Message(
                "Colleague",
                "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
            ),
            Message(
                "Colleague",
                "I didn't know you can now run the emulator directly from Android Studio"
            ),
            Message(
                "Colleague",
                "Compose Previews are great to check quickly how a composable layout looks like"
            ),
            Message(
                "Colleague",
                "Previews are also interactive after enabling the experimental setting"
            ),
            Message(
                "Colleague",
                "Have you tried writing build.gradle with KTS?"
            ),
        )
    }
}