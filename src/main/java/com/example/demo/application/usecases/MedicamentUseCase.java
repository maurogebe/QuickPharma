package com.example.demo.application.usecases;

import com.example.demo.application.dtos.MedicamentDTO;
import com.example.demo.application.exeptions.ApiRequestException;
import com.example.demo.application.mappers.MedicamentMapper;
import com.example.demo.domain.entities.Medicament;
import com.example.demo.domain.repositories.MedicamentRepository;
import com.ibm.watson.assistant.v1.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.StatefulMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MedicamentUseCase {

    private final MedicamentRepository medicamentRepository;
    private final WatsonService watsonService;

    @Autowired
    public MedicamentUseCase(MedicamentRepository medicamentRepository, WatsonService watsonService){
        this.medicamentRepository = medicamentRepository;
        this.watsonService = watsonService;
    }

    public MedicamentDTO createMedicament(MedicamentDTO medicament){
        Medicament medicamentSaved = this.medicamentRepository.save(MedicamentMapper.INSTANCE.medicamentDTOToMedicament(medicament));
        return MedicamentMapper.INSTANCE.medicamentToMedicamentDTO(medicamentSaved);
    }

    public List<MedicamentDTO> getMedicaments(String name){

        List<MedicamentDTO> medicaments;
        if(name == null) {
            medicaments = MedicamentMapper.INSTANCE.medicamentListToMedicamentDTOList(medicamentRepository.findAll());
        } else {
            medicaments = MedicamentMapper.INSTANCE.medicamentListToMedicamentDTOList(medicamentRepository.findAllByName(name));
        }

        if(!medicaments.isEmpty()) {
            String[] names = medicaments.stream()
                    .map(MedicamentDTO::getName)
                    .toArray(String[]::new);
            getMedicamentsInfo(names);
//            for (MedicamentDTO medicament : medicaments) {
//                String description = descriptions.getOrDefault(medicament.getName(), "Descripción no encontrada.");
//                if(medicament.getDescription().length() < 20) medicament.setDescription(description);
//            }
        }

        List<Medicament> medicamentList = medicamentRepository.findAll();
        return MedicamentMapper.INSTANCE.medicamentListToMedicamentDTOList(medicamentList);
    }

    private void getMedicamentsInfo(String[] names) {
        try {
            StringBuilder promptBuilder = new StringBuilder("Proporcione una descripción para cada uno de los siguientes medicamentos. Responda en español:\n");
            for (String name : names) {
                promptBuilder.append("- ").append(name).append("\n");
            }

            String prompt = promptBuilder.toString();
            StatefulMessageResponse response = watsonService.sendMessage("¿Qué es el Paracetamol?");

            System.out.println(response);
        } catch (RuntimeException e) {
            System.out.println(e);
        }

//        return parseDescriptions(response, names);
    }

//    private Map<String, String> parseDescriptions(String response, String[] names) {
//        Map<String, String> descriptions = new HashMap<>();
//        for (String name : names) {
//            String formattedName = name.toLowerCase().trim() + ":";
//
//            int startIndex = response.indexOf(formattedName);
//            if (startIndex != -1) {
//                startIndex += formattedName.length();
//                int endIndex = response.indexOf("\n", startIndex);
//                if (endIndex == -1) {
//                    endIndex = response.length();
//                }
//                String description = response.substring(startIndex, endIndex).trim();
//                descriptions.put(name, description);
//            } else {
//                descriptions.put(name, "Descripción no encontrada.");
//            }
//        }
//        return descriptions;
//    }

    public List<MedicamentDTO> getMedicamentsById(List<Long> ids){
        List<Medicament> medicamentList = medicamentRepository.findAllById(ids);
        return MedicamentMapper.INSTANCE.medicamentListToMedicamentDTOList(medicamentList);
    }

    public MedicamentDTO getMedicamentById(Long id){
        Optional<Medicament> medicament = medicamentRepository.findById(id);
        if(medicament.isEmpty()) throw new ApiRequestException("No se encontró medicamento con ID: " + id, HttpStatus.NOT_FOUND);
        return MedicamentMapper.INSTANCE.medicamentToMedicamentDTO(medicament.get());
    }

    public void deleteById(Long id){
        getMedicamentById(id);
        medicamentRepository.deleteById(id);
    }

    public MedicamentDTO updateMedicament(Long id, MedicamentDTO medicamentUpdate){
        MedicamentDTO medicamentById = getMedicamentById(id);

        medicamentById.setName(medicamentUpdate.getName());
        medicamentById.setDescription(medicamentUpdate.getDescription());
        medicamentById.setForm(medicamentUpdate.getForm());
        medicamentById.setStock(medicamentUpdate.getStock());
        medicamentById.setCost(medicamentUpdate.getCost());
        medicamentById.setPrescriptionRequired(medicamentUpdate.isPrescriptionRequired());

        Medicament medicament = medicamentRepository.save(MedicamentMapper.INSTANCE.medicamentDTOToMedicament(medicamentById));
        return MedicamentMapper.INSTANCE.medicamentToMedicamentDTO(medicament);
    }
}
