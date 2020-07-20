package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.scripts.*
import rat.poison.toLocale

class RanksTab : Tab(false, false) {
    private val table = VisTable(true)

    var ranksListTable = VisTable()

    var teamsLabel = VisLabel()
    var namesLabel = VisLabel()
    var ranksLabel = VisLabel()
    var killsLabel = VisLabel()
    var deathsLabel = VisLabel()
    var KDsLabel = VisLabel()
    var winsLabel = VisLabel()

    init {
        ranksListTable.add(teamsLabel)
        ranksListTable.add(namesLabel)
        ranksListTable.add(ranksLabel)
        ranksListTable.add(killsLabel)
        ranksListTable.add(deathsLabel)
        ranksListTable.add(KDsLabel)
        ranksListTable.add(winsLabel)

        table.add(ranksListTable).maxWidth(500F)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Ranks".toLocale()
    }

    fun updateRanks() {
        teamsLabel.setText("Team".toLocale() + "  \n")
        namesLabel.setText("Name".toLocale() + "  \n")
        ranksLabel.setText("Rank".toLocale() + "  \n")
        killsLabel.setText("Kills".toLocale() + "  \n")
        deathsLabel.setText("Deaths".toLocale() + "  \n")
        KDsLabel.setText("K/D".toLocale() + "  \n")
        winsLabel.setText("Wins".toLocale() + "  \n")

        for (i in 0 until teamList.size) {
            teamsLabel.setText(teamsLabel.text.toString() + teamList[i] + "  \n")
            namesLabel.setText(namesLabel.text.toString() + nameList[i] + "  \n")
            ranksLabel.setText(ranksLabel.text.toString() + rankList[i] + "  \n")
            killsLabel.setText(killsLabel.text.toString() + killsList[i] + "  \n")
            deathsLabel.setText(deathsLabel.text.toString() + deathsList[i] + "  \n")
            KDsLabel.setText(KDsLabel.text.toString() + KDList[i] + "  \n")
            winsLabel.setText(winsLabel.text.toString() + winsList[i] + "  \n")
        }
    }
}