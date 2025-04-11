const HomeV3Component = {
    data: function (){
        return{

        }
    },
    template:`
          <div class="campusdiv">
            <h1>Le nostre modalità di studio</h1>
            <p>Scegli il modo che più ti è comodo per frequentare le tue lezioni!</p>

            <div class="row">
              <div class="campus-col">
                <img src="./assets/studiodacasa.png">
                <div class="layer">
                  <h3>DA CASA</h3>
                </div>
              </div>
              <div class="campus-col">
                <img src="./assets/studioinpresenza.png">
                <div class="layer">
                  <h3>IN PRESENZA DI UN DOCENTE</h3>
                </div>
              </div>
              <div class="campus-col">
                <img src="./assets/studioingruppo.png">
                <div class="layer">
                  <h3>IN GRUPPO</h3>
                </div>
              </div>
            </div>
          </div>
        `
}