package uk.ac.bournemouth.ap.yahtzeebase.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import uk.ac.bournemouth.ap.yahtzeebase.lib.GameUpdateListener
import uk.ac.bournemouth.ap.yahtzeebase.lib.ScoreGroup
import uk.ac.bournemouth.ap.yahtzeebase.logic.StudentYahtzeeGame
import uk.ac.bournemouth.ap.yahtzeebase.ui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GameUpdateListener<StudentYahtzeeGame> {

    lateinit var scoreViews: Array<TextView>
    lateinit var scoreButtons: Array<Button>
    lateinit var dieViews: Array<ImageView>

    // TODO you want to have some sort of game property
    object game {
        var roundInTurn = 0
    }


    /** Simple accessor for the button associated with a scoregroup. */
    private val ScoreGroup.button: Button get() = scoreButtons[this.ordinal]

    /** Simple accessor for the value label associated with a scoregroup. */
    private val ScoreGroup.valueLabel: TextView get() = scoreViews[this.ordinal]

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRollDice.setOnClickListener { doRollDice() }
        binding.lblOnes.setOnClickListener { doCalculateOnes() }
        binding.lblTwos.setOnClickListener { doCalculateTwos() }
        binding.lblThrees.setOnClickListener { doCalculateThrees() }
        binding.lblFours.setOnClickListener { doCalculateFours() }
        binding.lblFives.setOnClickListener { doCalculateFives() }
        binding.lblSixes.setOnClickListener { doCalculateSixes() }
        binding.lblThreeOfAKind.setOnClickListener { doCalculateThreeOfAKind() }
        binding.lblFourOfAKind.setOnClickListener { doCalculateFourOfAKind() }
        binding.lblFullHouse.setOnClickListener { doCalculateFullHouse() }
        binding.lblSmallStraight.setOnClickListener { doCalculateSmallStraight() }
        binding.lblLargeStraight.setOnClickListener { doCalculateLargeStraight() }
        binding.lblYahtzee.setOnClickListener { doCalculateYahtzee() }
        binding.lblChance.setOnClickListener { doCalculateChance() }

        // TODO Listen to game update events (to update the views)
        // TODO Listen to game finish events (to be able to show a win dialog/lock the UI)

        // Just some convenient ways to get at the ui controls. As an array is so much easier than
        // as individual views.
        with(binding) {
            dieViews = arrayOf(die1, die2, die3, die4, die5)

            // Same for score buttons. They all work the same, so treat them the same and use an
            // array and loops for them.
            /*scoreButtons = Array(ScoreGroup.values().size) { groupNo ->
                when (ScoreGroup.values()[groupNo]) {
                    ScoreGroup.ONES -> lblOnes
                    ScoreGroup.TWOS -> lblTwos
                    ScoreGroup.THREES -> lblThrees
                    ScoreGroup.FOURS -> lblFours
                    ScoreGroup.FIVES -> lblFives
                    ScoreGroup.SIXES -> lblSixes
                    ScoreGroup.THREE_OF_A_KIND -> lblThreeOfAKind
                    ScoreGroup.FOUR_OF_A_KIND -> lblFourOfAKind
                    ScoreGroup.FULL_HOUSE -> lblFullHouse
                    ScoreGroup.SMALL_STRAIGHT -> lblSmallStraight
                    ScoreGroup.LARGE_STRAIGHT -> lblLargeStraight
                    ScoreGroup.YAHTZEE -> lblYahtzee
                    ScoreGroup.CHANCE -> lblChance
                }
            }

            // Like the buttons the views that display the scores for specific groups need to be captured.
            scoreViews = Array(ScoreGroup.values().size) { groupNo ->
                when (ScoreGroup.values()[groupNo]) {
                    ScoreGroup.ONES -> lblValOnes
                    ScoreGroup.TWOS -> lblValTwos
                    ScoreGroup.THREES -> lblValThrees
                    ScoreGroup.FOURS -> lblValFours
                    ScoreGroup.FIVES -> lblValFives
                    ScoreGroup.SIXES -> lblValSixes
                    ScoreGroup.THREE_OF_A_KIND -> lblValThreeOfAKind
                    ScoreGroup.FOUR_OF_A_KIND -> lblValFourOfAKind
                    ScoreGroup.FULL_HOUSE -> lblValFullHouse
                    ScoreGroup.SMALL_STRAIGHT -> lblValSmallStraight
                    ScoreGroup.LARGE_STRAIGHT -> lblValLargeStraight
                    ScoreGroup.YAHTZEE -> lblValYahtzee
                    ScoreGroup.CHANCE -> lblValChance
                }
            }
        }

        // Make sure to listen to each button. The lambda allows us to pass along the group
        for (group in ScoreGroup.values()) {
            group.button.setOnClickListener { doChooseGroup(group) }
        } */

            for (dieView in dieViews) {
                dieView.setOnClickListener {
                    // This will allow the dies to be selected/deselected

                    if (game.roundInTurn == 1 || game.roundInTurn == 2) {
                        it.isSelected = !it.isSelected
                    }

                }
            }
        }

        // TODO: Trigger the update once to init the view.
    }

    private fun doChooseGroup(scoreGroup: ScoreGroup) {
        TODO("Handle the choosing of the scoring group by calling the right function on the game")
    }

    var diceValues = arrayOf(0, 0, 0, 0, 0)

    private fun doRollDice() {
        if (MainActivity.game.roundInTurn == 0 || MainActivity.game.roundInTurn == 1) {
            MainActivity.game.roundInTurn = MainActivity.game.roundInTurn + 1
            val dice = Dice(6)
            val diceRoll1 = dice.roll()
            val diceRoll2 = dice.roll()
            val diceRoll3 = dice.roll()
            val diceRoll4 = dice.roll()
            val diceRoll5 = dice.roll()
            val taskView: TextView = findViewById(R.id.taskView)
            taskView.text = "Select the dice you would like to keep"
            if (!dieViews[0].isSelected) {
                diceValues[0] = diceRoll1
                val diceImage1: ImageView = findViewById(R.id.die1)
                diceImage1.contentDescription = diceRoll1.toString()
                when (diceRoll1) {
                    1 -> diceImage1.setImageResource(R.drawable.ic_die1)
                    2 -> diceImage1.setImageResource(R.drawable.ic_die2)
                    3 -> diceImage1.setImageResource(R.drawable.ic_die3)
                    4 -> diceImage1.setImageResource(R.drawable.ic_die4)
                    5 -> diceImage1.setImageResource(R.drawable.ic_die5)
                    6 -> diceImage1.setImageResource(R.drawable.ic_die6)
                }
            }
            if (!dieViews[1].isSelected) {
                diceValues[1] = diceRoll2
                val diceImage2: ImageView = findViewById(R.id.die2)
                diceImage2.contentDescription = diceRoll2.toString()
                when (diceRoll2) {
                    1 -> diceImage2.setImageResource(R.drawable.ic_die1)
                    2 -> diceImage2.setImageResource(R.drawable.ic_die2)
                    3 -> diceImage2.setImageResource(R.drawable.ic_die3)
                    4 -> diceImage2.setImageResource(R.drawable.ic_die4)
                    5 -> diceImage2.setImageResource(R.drawable.ic_die5)
                    6 -> diceImage2.setImageResource(R.drawable.ic_die6)
                }
            }
            if (!dieViews[2].isSelected) {
                diceValues[2] = diceRoll3
                val diceImage3: ImageView = findViewById(R.id.die3)
                diceImage3.contentDescription = diceRoll3.toString()
                when (diceRoll3) {
                    1 -> diceImage3.setImageResource(R.drawable.ic_die1)
                    2 -> diceImage3.setImageResource(R.drawable.ic_die2)
                    3 -> diceImage3.setImageResource(R.drawable.ic_die3)
                    4 -> diceImage3.setImageResource(R.drawable.ic_die4)
                    5 -> diceImage3.setImageResource(R.drawable.ic_die5)
                    6 -> diceImage3.setImageResource(R.drawable.ic_die6)
                }
            }
            if (!dieViews[3].isSelected) {
                diceValues[3] = diceRoll4
                val diceImage4: ImageView = findViewById(R.id.die4)
                diceImage4.contentDescription = diceRoll4.toString()
                when (diceRoll4) {
                    1 -> diceImage4.setImageResource(R.drawable.ic_die1)
                    2 -> diceImage4.setImageResource(R.drawable.ic_die2)
                    3 -> diceImage4.setImageResource(R.drawable.ic_die3)
                    4 -> diceImage4.setImageResource(R.drawable.ic_die4)
                    5 -> diceImage4.setImageResource(R.drawable.ic_die5)
                    6 -> diceImage4.setImageResource(R.drawable.ic_die6)
                }
            }
            if (!dieViews[4].isSelected) {
                diceValues[4] = diceRoll5
                val diceImage5: ImageView = findViewById(R.id.die5)
                diceImage5.contentDescription = diceRoll5.toString()
                when (diceRoll5) {
                    1 -> diceImage5.setImageResource(R.drawable.ic_die1)
                    2 -> diceImage5.setImageResource(R.drawable.ic_die2)
                    3 -> diceImage5.setImageResource(R.drawable.ic_die3)
                    4 -> diceImage5.setImageResource(R.drawable.ic_die4)
                    5 -> diceImage5.setImageResource(R.drawable.ic_die5)
                    6 -> diceImage5.setImageResource(R.drawable.ic_die6)
                }
            }
        } else if (MainActivity.game.roundInTurn == 2) {
            MainActivity.game.roundInTurn = MainActivity.game.roundInTurn + 1
            val dice = Dice(6)
            val diceRoll1 = dice.roll()
            val diceRoll2 = dice.roll()
            val diceRoll3 = dice.roll()
            val diceRoll4 = dice.roll()
            val diceRoll5 = dice.roll()
            val taskView: TextView = findViewById(R.id.taskView)
            taskView.text = "Now select the scoring options from the lower and upper groups"
            if (!dieViews[0].isSelected) {
                diceValues[0] = diceRoll1
                val diceImage1: ImageView = findViewById(R.id.die1)
                diceImage1.contentDescription = diceRoll1.toString()
                when (diceRoll1) {
                    1 -> diceImage1.setImageResource(R.drawable.ic_die1)
                    2 -> diceImage1.setImageResource(R.drawable.ic_die2)
                    3 -> diceImage1.setImageResource(R.drawable.ic_die3)
                    4 -> diceImage1.setImageResource(R.drawable.ic_die4)
                    5 -> diceImage1.setImageResource(R.drawable.ic_die5)
                    6 -> diceImage1.setImageResource(R.drawable.ic_die6)
                }
            }
            if (!dieViews[1].isSelected) {
                diceValues[1] = diceRoll2
                val diceImage2: ImageView = findViewById(R.id.die2)
                diceImage2.contentDescription = diceRoll2.toString()
                when (diceRoll2) {
                    1 -> diceImage2.setImageResource(R.drawable.ic_die1)
                    2 -> diceImage2.setImageResource(R.drawable.ic_die2)
                    3 -> diceImage2.setImageResource(R.drawable.ic_die3)
                    4 -> diceImage2.setImageResource(R.drawable.ic_die4)
                    5 -> diceImage2.setImageResource(R.drawable.ic_die5)
                    6 -> diceImage2.setImageResource(R.drawable.ic_die6)
                }
            }
            if (!dieViews[2].isSelected) {
                diceValues[2] = diceRoll3
                val diceImage3: ImageView = findViewById(R.id.die3)
                diceImage3.contentDescription = diceRoll3.toString()
                when (diceRoll3) {
                    1 -> diceImage3.setImageResource(R.drawable.ic_die1)
                    2 -> diceImage3.setImageResource(R.drawable.ic_die2)
                    3 -> diceImage3.setImageResource(R.drawable.ic_die3)
                    4 -> diceImage3.setImageResource(R.drawable.ic_die4)
                    5 -> diceImage3.setImageResource(R.drawable.ic_die5)
                    6 -> diceImage3.setImageResource(R.drawable.ic_die6)
                }
            }
            if (!dieViews[3].isSelected) {
                diceValues[3] = diceRoll4
                val diceImage4: ImageView = findViewById(R.id.die4)
                diceImage4.contentDescription = diceRoll4.toString()
                when (diceRoll4) {
                    1 -> diceImage4.setImageResource(R.drawable.ic_die1)
                    2 -> diceImage4.setImageResource(R.drawable.ic_die2)
                    3 -> diceImage4.setImageResource(R.drawable.ic_die3)
                    4 -> diceImage4.setImageResource(R.drawable.ic_die4)
                    5 -> diceImage4.setImageResource(R.drawable.ic_die5)
                    6 -> diceImage4.setImageResource(R.drawable.ic_die6)
                }
            }
            if (!dieViews[4].isSelected) {
                diceValues[4] = diceRoll5
                val diceImage5: ImageView = findViewById(R.id.die5)
                diceImage5.contentDescription = diceRoll5.toString()
                when (diceRoll5) {
                    1 -> diceImage5.setImageResource(R.drawable.ic_die1)
                    2 -> diceImage5.setImageResource(R.drawable.ic_die2)
                    3 -> diceImage5.setImageResource(R.drawable.ic_die3)
                    4 -> diceImage5.setImageResource(R.drawable.ic_die4)
                    5 -> diceImage5.setImageResource(R.drawable.ic_die5)
                    6 -> diceImage5.setImageResource(R.drawable.ic_die6)
                }
            }
            if (dieViews[0].isSelected) {
                dieViews[0].isSelected = !dieViews[0].isSelected
            }
            if (dieViews[1].isSelected) {
                dieViews[1].isSelected = !dieViews[1].isSelected
            }
            if (dieViews[2].isSelected) {
                dieViews[2].isSelected = !dieViews[2].isSelected
            }
            if (dieViews[3].isSelected) {
                dieViews[3].isSelected = !dieViews[3].isSelected
            }
            if (dieViews[4].isSelected) {
                dieViews[4].isSelected = !dieViews[4].isSelected
            }
        }
    }

    private fun doCalculateOnes() {
        var numOfOnes = 0
        if (diceValues[0] == 1) { numOfOnes += 1 }
        if (diceValues[1] == 1) { numOfOnes += 1 }
        if (diceValues[2] == 1) { numOfOnes += 1 }
        if (diceValues[3] == 1) { numOfOnes += 1 }
        if (diceValues[4] == 1) { numOfOnes += 1 }
        val upperSubtotal: TextView = findViewById(R.id.lblUpperSubtotal)
        val upperTotal: TextView = findViewById(R.id.lblUpperTotal)
        upperSubtotal.text = numOfOnes.toString()
        upperTotal.text = numOfOnes.toString()
    }

    private fun doCalculateTwos() {
        var numOfTwos = 0
        if (diceValues[0] == 2) { numOfTwos += 1 }
        if (diceValues[1] == 2) { numOfTwos += 1 }
        if (diceValues[2] == 2) { numOfTwos += 1 }
        if (diceValues[3] == 2) { numOfTwos += 1 }
        if (diceValues[4] == 2) { numOfTwos += 1 }
        var valOfTwos = numOfTwos * 2
        val upperSubtotal: TextView = findViewById(R.id.lblUpperSubtotal)
        val upperTotal: TextView = findViewById(R.id.lblUpperTotal)
        upperSubtotal.text = valOfTwos.toString()
        upperTotal.text = valOfTwos.toString()
    }

    private fun doCalculateThrees() {
        var numOfThrees = 0
        if (diceValues[0] == 3) { numOfThrees += 1 }
        if (diceValues[1] == 3) { numOfThrees += 1 }
        if (diceValues[2] == 3) { numOfThrees += 1 }
        if (diceValues[3] == 3) { numOfThrees += 1 }
        if (diceValues[4] == 3) { numOfThrees += 1 }
        var valOfThrees = numOfThrees * 3
        val upperSubtotal: TextView = findViewById(R.id.lblUpperSubtotal)
        val upperTotal: TextView = findViewById(R.id.lblUpperTotal)
        upperSubtotal.text = valOfThrees.toString()
        upperTotal.text = valOfThrees.toString()
    }

    private fun doCalculateFours() {
        var numOfFours = 0
        if (diceValues[0] == 4) { numOfFours += 1 }
        if (diceValues[1] == 4) { numOfFours += 1 }
        if (diceValues[2] == 4) { numOfFours += 1 }
        if (diceValues[3] == 4) { numOfFours += 1 }
        if (diceValues[4] == 4) { numOfFours += 1 }
        var valOfFours = numOfFours * 4
        val upperSubtotal: TextView = findViewById(R.id.lblUpperSubtotal)
        val upperTotal: TextView = findViewById(R.id.lblUpperTotal)
        upperSubtotal.text = valOfFours.toString()
        upperTotal.text = valOfFours.toString()
    }

    private fun doCalculateFives() {
        var numOfFives = 0
        if (diceValues[0] == 5) { numOfFives += 1 }
        if (diceValues[1] == 5) { numOfFives += 1 }
        if (diceValues[2] == 5) { numOfFives += 1 }
        if (diceValues[3] == 5) { numOfFives += 1 }
        if (diceValues[4] == 5) { numOfFives += 1 }
        var valOfFives = numOfFives * 5
        val upperSubtotal: TextView = findViewById(R.id.lblUpperSubtotal)
        val upperTotal: TextView = findViewById(R.id.lblUpperTotal)
        upperSubtotal.text = valOfFives.toString()
        upperTotal.text = valOfFives.toString()
    }

    private fun doCalculateSixes() {
        var numOfSixes = 0
        if (diceValues[0] == 6) { numOfSixes += 1 }
        if (diceValues[1] == 6) { numOfSixes += 1 }
        if (diceValues[2] == 6) { numOfSixes += 1 }
        if (diceValues[3] == 6) { numOfSixes += 1 }
        if (diceValues[4] == 6) { numOfSixes += 1 }
        var valOfSixes = numOfSixes * 6
        val upperSubtotal: TextView = findViewById(R.id.lblUpperSubtotal)
        val upperTotal: TextView = findViewById(R.id.lblUpperTotal)
        upperSubtotal.text = valOfSixes.toString()
        upperTotal.text = valOfSixes.toString()
    }

    private fun doCalculateThreeOfAKind() {
        var numOfOnes = 0
        if (diceValues[0] == 1) { numOfOnes += 1 }
        if (diceValues[1] == 1) { numOfOnes += 1 }
        if (diceValues[2] == 1) { numOfOnes += 1 }
        if (diceValues[3] == 1) { numOfOnes += 1 }
        if (diceValues[4] == 1) { numOfOnes += 1 }
        var numOfTwos = 0
        if (diceValues[0] == 2) { numOfTwos += 1 }
        if (diceValues[1] == 2) { numOfTwos += 1 }
        if (diceValues[2] == 2) { numOfTwos += 1 }
        if (diceValues[3] == 2) { numOfTwos += 1 }
        if (diceValues[4] == 2) { numOfTwos += 1 }
        var numOfThrees = 0
        if (diceValues[0] == 3) { numOfThrees += 1 }
        if (diceValues[1] == 3) { numOfThrees += 1 }
        if (diceValues[2] == 3) { numOfThrees += 1 }
        if (diceValues[3] == 3) { numOfThrees += 1 }
        if (diceValues[4] == 3) { numOfThrees += 1 }
        var numOfFours = 0
        if (diceValues[0] == 4) { numOfFours += 1 }
        if (diceValues[1] == 4) { numOfFours += 1 }
        if (diceValues[2] == 4) { numOfFours += 1 }
        if (diceValues[3] == 4) { numOfFours += 1 }
        if (diceValues[4] == 4) { numOfFours += 1 }
        var numOfFives = 0
        if (diceValues[0] == 5) { numOfFives += 1 }
        if (diceValues[1] == 5) { numOfFives += 1 }
        if (diceValues[2] == 5) { numOfFives += 1 }
        if (diceValues[3] == 5) { numOfFives += 1 }
        if (diceValues[4] == 5) { numOfFives += 1 }
        var numOfSixes = 0
        if (diceValues[0] == 6) { numOfSixes += 1 }
        if (diceValues[1] == 6) { numOfSixes += 1 }
        if (diceValues[2] == 6) { numOfSixes += 1 }
        if (diceValues[3] == 6) { numOfSixes += 1 }
        if (diceValues[4] == 6) { numOfSixes += 1 }
        val lowerTotal: TextView = findViewById(R.id.lblLowerTotal)
        if (numOfOnes > 2) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else if (numOfTwos > 2) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else if (numOfThrees > 2) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else if (numOfFours > 2) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else if (numOfFives > 2) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else if (numOfSixes > 2) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else {
            lowerTotal.text = "0"
        }
    }

    private fun doCalculateFourOfAKind() {
        var numOfOnes = 0
        if (diceValues[0] == 1) { numOfOnes += 1 }
        if (diceValues[1] == 1) { numOfOnes += 1 }
        if (diceValues[2] == 1) { numOfOnes += 1 }
        if (diceValues[3] == 1) { numOfOnes += 1 }
        if (diceValues[4] == 1) { numOfOnes += 1 }
        var numOfTwos = 0
        if (diceValues[0] == 2) { numOfTwos += 1 }
        if (diceValues[1] == 2) { numOfTwos += 1 }
        if (diceValues[2] == 2) { numOfTwos += 1 }
        if (diceValues[3] == 2) { numOfTwos += 1 }
        if (diceValues[4] == 2) { numOfTwos += 1 }
        var numOfThrees = 0
        if (diceValues[0] == 3) { numOfThrees += 1 }
        if (diceValues[1] == 3) { numOfThrees += 1 }
        if (diceValues[2] == 3) { numOfThrees += 1 }
        if (diceValues[3] == 3) { numOfThrees += 1 }
        if (diceValues[4] == 3) { numOfThrees += 1 }
        var numOfFours = 0
        if (diceValues[0] == 4) { numOfFours += 1 }
        if (diceValues[1] == 4) { numOfFours += 1 }
        if (diceValues[2] == 4) { numOfFours += 1 }
        if (diceValues[3] == 4) { numOfFours += 1 }
        if (diceValues[4] == 4) { numOfFours += 1 }
        var numOfFives = 0
        if (diceValues[0] == 5) { numOfFives += 1 }
        if (diceValues[1] == 5) { numOfFives += 1 }
        if (diceValues[2] == 5) { numOfFives += 1 }
        if (diceValues[3] == 5) { numOfFives += 1 }
        if (diceValues[4] == 5) { numOfFives += 1 }
        var numOfSixes = 0
        if (diceValues[0] == 6) { numOfSixes += 1 }
        if (diceValues[1] == 6) { numOfSixes += 1 }
        if (diceValues[2] == 6) { numOfSixes += 1 }
        if (diceValues[3] == 6) { numOfSixes += 1 }
        if (diceValues[4] == 6) { numOfSixes += 1 }
        val lowerTotal: TextView = findViewById(R.id.lblLowerTotal)
        if (numOfOnes > 3) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else if (numOfTwos > 3) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else if (numOfThrees > 3) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else if (numOfFours > 3) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else if (numOfFives > 3) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else if (numOfSixes > 3) {
            var lowerScore = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
            lowerTotal.text = lowerScore.toString()
        }
        else {
            lowerTotal.text = "0"
        }
    }

    private fun doCalculateFullHouse() {
        var numOfOnes = 0
        if (diceValues[0] == 1) { numOfOnes += 1 }
        if (diceValues[1] == 1) { numOfOnes += 1 }
        if (diceValues[2] == 1) { numOfOnes += 1 }
        if (diceValues[3] == 1) { numOfOnes += 1 }
        if (diceValues[4] == 1) { numOfOnes += 1 }
        var numOfTwos = 0
        if (diceValues[0] == 2) { numOfTwos += 1 }
        if (diceValues[1] == 2) { numOfTwos += 1 }
        if (diceValues[2] == 2) { numOfTwos += 1 }
        if (diceValues[3] == 2) { numOfTwos += 1 }
        if (diceValues[4] == 2) { numOfTwos += 1 }
        var numOfThrees = 0
        if (diceValues[0] == 3) { numOfThrees += 1 }
        if (diceValues[1] == 3) { numOfThrees += 1 }
        if (diceValues[2] == 3) { numOfThrees += 1 }
        if (diceValues[3] == 3) { numOfThrees += 1 }
        if (diceValues[4] == 3) { numOfThrees += 1 }
        var numOfFours = 0
        if (diceValues[0] == 4) { numOfFours += 1 }
        if (diceValues[1] == 4) { numOfFours += 1 }
        if (diceValues[2] == 4) { numOfFours += 1 }
        if (diceValues[3] == 4) { numOfFours += 1 }
        if (diceValues[4] == 4) { numOfFours += 1 }
        var numOfFives = 0
        if (diceValues[0] == 5) { numOfFives += 1 }
        if (diceValues[1] == 5) { numOfFives += 1 }
        if (diceValues[2] == 5) { numOfFives += 1 }
        if (diceValues[3] == 5) { numOfFives += 1 }
        if (diceValues[4] == 5) { numOfFives += 1 }
        var numOfSixes = 0
        if (diceValues[0] == 6) { numOfSixes += 1 }
        if (diceValues[1] == 6) { numOfSixes += 1 }
        if (diceValues[2] == 6) { numOfSixes += 1 }
        if (diceValues[3] == 6) { numOfSixes += 1 }
        if (diceValues[4] == 6) { numOfSixes += 1 }
        val lowerTotal: TextView = findViewById(R.id.lblLowerTotal)
        if (numOfOnes == 3) {
            if (numOfTwos == 2 || numOfThrees == 2 || numOfFours == 2 || numOfFives == 2 || numOfSixes == 2) {
                lowerTotal.text = "25"
            }
            else { lowerTotal.text = "0" }
        }
        else if (numOfTwos == 3) {
            if (numOfOnes == 2 || numOfThrees == 2 || numOfFours == 2 || numOfFives == 2 || numOfSixes == 2) {
                lowerTotal.text = "25"
            }
            else { lowerTotal.text = "0" }
        }
        else if (numOfThrees == 3) {
            if (numOfOnes == 2 || numOfTwos == 2 || numOfFours == 2 || numOfFives == 2 || numOfSixes == 2) {
                lowerTotal.text = "25"
            }
            else { lowerTotal.text = "0" }
        }
        else if (numOfFours == 3) {
            if (numOfOnes == 2 || numOfTwos == 2 || numOfThrees == 2 || numOfFives == 2 || numOfSixes == 2) {
                lowerTotal.text = "25"
            }
            else { lowerTotal.text = "0" }
        }
        else if (numOfFives == 3) {
            if (numOfOnes == 2 || numOfTwos == 2 || numOfThrees == 2 || numOfFours == 2 || numOfSixes == 2) {
                lowerTotal.text = "25"
            }
            else { lowerTotal.text = "0" }
        }
        else if (numOfSixes == 3) {
            if (numOfOnes == 2 || numOfTwos == 2 || numOfThrees == 2 || numOfFours == 2 || numOfFives == 2) {
                lowerTotal.text = "25"
            }
            else { lowerTotal.text = "0" }
        }
        else { lowerTotal.text = "0" }
    }

    private fun doCalculateSmallStraight() {
        var numOfOnes = 0
        if (diceValues[0] == 1) { numOfOnes += 1 }
        if (diceValues[1] == 1) { numOfOnes += 1 }
        if (diceValues[2] == 1) { numOfOnes += 1 }
        if (diceValues[3] == 1) { numOfOnes += 1 }
        if (diceValues[4] == 1) { numOfOnes += 1 }
        var numOfTwos = 0
        if (diceValues[0] == 2) { numOfTwos += 1 }
        if (diceValues[1] == 2) { numOfTwos += 1 }
        if (diceValues[2] == 2) { numOfTwos += 1 }
        if (diceValues[3] == 2) { numOfTwos += 1 }
        if (diceValues[4] == 2) { numOfTwos += 1 }
        var numOfThrees = 0
        if (diceValues[0] == 3) { numOfThrees += 1 }
        if (diceValues[1] == 3) { numOfThrees += 1 }
        if (diceValues[2] == 3) { numOfThrees += 1 }
        if (diceValues[3] == 3) { numOfThrees += 1 }
        if (diceValues[4] == 3) { numOfThrees += 1 }
        var numOfFours = 0
        if (diceValues[0] == 4) { numOfFours += 1 }
        if (diceValues[1] == 4) { numOfFours += 1 }
        if (diceValues[2] == 4) { numOfFours += 1 }
        if (diceValues[3] == 4) { numOfFours += 1 }
        if (diceValues[4] == 4) { numOfFours += 1 }
        var numOfFives = 0
        if (diceValues[0] == 5) { numOfFives += 1 }
        if (diceValues[1] == 5) { numOfFives += 1 }
        if (diceValues[2] == 5) { numOfFives += 1 }
        if (diceValues[3] == 5) { numOfFives += 1 }
        if (diceValues[4] == 5) { numOfFives += 1 }
        var numOfSixes = 0
        if (diceValues[0] == 6) { numOfSixes += 1 }
        if (diceValues[1] == 6) { numOfSixes += 1 }
        if (diceValues[2] == 6) { numOfSixes += 1 }
        if (diceValues[3] == 6) { numOfSixes += 1 }
        if (diceValues[4] == 6) { numOfSixes += 1 }
        val lowerTotal: TextView = findViewById(R.id.lblLowerTotal)
        if (numOfOnes >= 1 && numOfTwos >= 1 && numOfThrees >= 1 && numOfFours >=1) {
            lowerTotal.text = "30"
        }
        else if (numOfTwos >= 1 && numOfThrees >= 1 && numOfFours >= 1 && numOfFives >=1) {
            lowerTotal.text = "30"
        }
        else if (numOfThrees >= 1 && numOfFours >= 1 && numOfFives >= 1 && numOfSixes >=1) {
            lowerTotal.text = "30"
        }
        else { lowerTotal.text = "0" }
    }

    private fun doCalculateLargeStraight() {
        var numOfOnes = 0
        if (diceValues[0] == 1) { numOfOnes += 1 }
        if (diceValues[1] == 1) { numOfOnes += 1 }
        if (diceValues[2] == 1) { numOfOnes += 1 }
        if (diceValues[3] == 1) { numOfOnes += 1 }
        if (diceValues[4] == 1) { numOfOnes += 1 }
        var numOfTwos = 0
        if (diceValues[0] == 2) { numOfTwos += 1 }
        if (diceValues[1] == 2) { numOfTwos += 1 }
        if (diceValues[2] == 2) { numOfTwos += 1 }
        if (diceValues[3] == 2) { numOfTwos += 1 }
        if (diceValues[4] == 2) { numOfTwos += 1 }
        var numOfThrees = 0
        if (diceValues[0] == 3) { numOfThrees += 1 }
        if (diceValues[1] == 3) { numOfThrees += 1 }
        if (diceValues[2] == 3) { numOfThrees += 1 }
        if (diceValues[3] == 3) { numOfThrees += 1 }
        if (diceValues[4] == 3) { numOfThrees += 1 }
        var numOfFours = 0
        if (diceValues[0] == 4) { numOfFours += 1 }
        if (diceValues[1] == 4) { numOfFours += 1 }
        if (diceValues[2] == 4) { numOfFours += 1 }
        if (diceValues[3] == 4) { numOfFours += 1 }
        if (diceValues[4] == 4) { numOfFours += 1 }
        var numOfFives = 0
        if (diceValues[0] == 5) { numOfFives += 1 }
        if (diceValues[1] == 5) { numOfFives += 1 }
        if (diceValues[2] == 5) { numOfFives += 1 }
        if (diceValues[3] == 5) { numOfFives += 1 }
        if (diceValues[4] == 5) { numOfFives += 1 }
        var numOfSixes = 0
        if (diceValues[0] == 6) { numOfSixes += 1 }
        if (diceValues[1] == 6) { numOfSixes += 1 }
        if (diceValues[2] == 6) { numOfSixes += 1 }
        if (diceValues[3] == 6) { numOfSixes += 1 }
        if (diceValues[4] == 6) { numOfSixes += 1 }
        val lowerTotal: TextView = findViewById(R.id.lblLowerTotal)
        if (numOfOnes >= 1 && numOfTwos >= 1 && numOfThrees >= 1 && numOfFours >=1 && numOfFives >= 1) {
            lowerTotal.text = "40"
        }
        else if (numOfTwos >= 1 && numOfThrees >= 1 && numOfFours >= 1 && numOfFives >=1 && numOfSixes >= 1) {
            lowerTotal.text = "40"
        }
        else { lowerTotal.text = "0" }
    }

    private fun doCalculateYahtzee() {
        var numOfOnes = 0
        if (diceValues[0] == 1) { numOfOnes += 1 }
        if (diceValues[1] == 1) { numOfOnes += 1 }
        if (diceValues[2] == 1) { numOfOnes += 1 }
        if (diceValues[3] == 1) { numOfOnes += 1 }
        if (diceValues[4] == 1) { numOfOnes += 1 }
        var numOfTwos = 0
        if (diceValues[0] == 2) { numOfTwos += 1 }
        if (diceValues[1] == 2) { numOfTwos += 1 }
        if (diceValues[2] == 2) { numOfTwos += 1 }
        if (diceValues[3] == 2) { numOfTwos += 1 }
        if (diceValues[4] == 2) { numOfTwos += 1 }
        var numOfThrees = 0
        if (diceValues[0] == 3) { numOfThrees += 1 }
        if (diceValues[1] == 3) { numOfThrees += 1 }
        if (diceValues[2] == 3) { numOfThrees += 1 }
        if (diceValues[3] == 3) { numOfThrees += 1 }
        if (diceValues[4] == 3) { numOfThrees += 1 }
        var numOfFours = 0
        if (diceValues[0] == 4) { numOfFours += 1 }
        if (diceValues[1] == 4) { numOfFours += 1 }
        if (diceValues[2] == 4) { numOfFours += 1 }
        if (diceValues[3] == 4) { numOfFours += 1 }
        if (diceValues[4] == 4) { numOfFours += 1 }
        var numOfFives = 0
        if (diceValues[0] == 5) { numOfFives += 1 }
        if (diceValues[1] == 5) { numOfFives += 1 }
        if (diceValues[2] == 5) { numOfFives += 1 }
        if (diceValues[3] == 5) { numOfFives += 1 }
        if (diceValues[4] == 5) { numOfFives += 1 }
        var numOfSixes = 0
        if (diceValues[0] == 6) { numOfSixes += 1 }
        if (diceValues[1] == 6) { numOfSixes += 1 }
        if (diceValues[2] == 6) { numOfSixes += 1 }
        if (diceValues[3] == 6) { numOfSixes += 1 }
        if (diceValues[4] == 6) { numOfSixes += 1 }
        val lowerTotal: TextView = findViewById(R.id.lblLowerTotal)
        if (numOfOnes > 4) { lowerTotal.text = "50" }
        else if (numOfTwos > 4) { lowerTotal.text = "50" }
        else if (numOfThrees > 4) { lowerTotal.text = "50" }
        else if (numOfFours > 4) { lowerTotal.text = "50" }
        else if (numOfFives > 4) { lowerTotal.text = "50" }
        else if (numOfSixes > 4) { lowerTotal.text = "50" }
        else { lowerTotal.text = "0" }
    }

    private fun doCalculateChance() {
        val lowerTotal: TextView = findViewById(R.id.lblLowerTotal)
        val lowerTotalValue = diceValues[0] + diceValues[1] + diceValues[2] + diceValues[3] + diceValues[4]
        lowerTotal.text = lowerTotalValue.toString()
    }

    override fun onGameUpdated(game: StudentYahtzeeGame) {
        TODO("Update all the game display when the game is updated")
    }

    companion object {
        /* In onGameUpdated you may want to display the dice, using this array (and appropriate image
           resources makes that quite easy to do. */

        val DIE_IMAGES = intArrayOf(
            R.drawable.ic_unknowndie, R.drawable.ic_die1, R.drawable.ic_die2, R.drawable.ic_die3,
            R.drawable.ic_die4, R.drawable.ic_die5, R.drawable.ic_die6
        )


    }
}

class Dice(private val numSides: Int) {

    fun roll(): Int {
        return (1..numSides).random()
    }
}




