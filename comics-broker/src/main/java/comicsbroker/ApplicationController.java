package comicsbroker;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import comicsbroker.VwComicVineVolumes.VwComicvineVolumes;
import comicsbroker.VwComicVineVolumes.VwComicvineVolumesRepository;
import comicsbroker.comics.BrokerComicsService;

@Controller
public class ApplicationController {
	
	@Autowired
	private VwComicvineVolumesRepository vwComicvineVolumesRepository;

	@Autowired
	private BrokerComicsService brokerComicsService;

	@GetMapping({"/"} )
    public String home() {
        return "home/home";
    }

	@GetMapping({"/volumes-check"})
	public String getVolumesCheck(Model model) { 

		List<VwComicvineVolumes> vwComicvineVolumesList = vwComicvineVolumesRepository.findAll();
		model.addAttribute("vwComicvineVolumesList", vwComicvineVolumesList);
        return "home/volumes-check";
	}

	@GetMapping({"/volumes-check/volume/{comicvineVolume}"})
	public String getgetVolumesCheckVolume(
		@PathVariable("comicvineVolume") String comicvineVolume,
		Model model) { 

		List<VwComicvineVolumes> vwComicvineVolumesList = vwComicvineVolumesRepository.findByComicvineVolume(comicvineVolume);
		if (vwComicvineVolumesList.size() == 1) {
			model.addAttribute("vwComicvineVolumes", vwComicvineVolumesList.get(0));
		}
        return "home/volumes-check-volume";
	}

	@GetMapping({"/volume/{comicvineVolume}/checkOnComicVineApi"})
	public String getcheckOnComicVineApi(
		@PathVariable("comicvineVolume") String comicvineVolume,
		Model model) { 

		brokerComicsService.checkComicsVolumeOnComicVineApi(comicvineVolume);
		
        return "redirect://volumes-check/volume/" + comicvineVolume;
	}

	
}