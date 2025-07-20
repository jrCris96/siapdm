package net.crisjr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.crisjr.model.Usuario;
import net.crisjr.model.Vehiculo;

public interface VehiculosRepository extends JpaRepository<Vehiculo, Integer> {
    long countByUsuarioId(Usuario usuario);
    List<Vehiculo> findByUsuarioId_Id(Integer idUsuario);
    int countByUsuarioId_Id(Integer idUsuario);

    @Query("SELECT COUNT(v) FROM Vehiculo v WHERE v.usuarioId = :usuario AND (:vehiculoId IS NULL OR v.id <> :vehiculoId)")
    long countByUsuarioIdExceptVehiculoId(@Param("usuario") Usuario usuario, @Param("vehiculoId") Integer vehiculoId);

    @Query("SELECT COUNT(v) FROM Vehiculo v WHERE v.usuarioId = :usuario AND v.estado = 'ACTIVO' AND (:excluirId IS NULL OR v.id != :excluirId)")
    long countVehiculosActivosByUsuario(@Param("usuario") Usuario usuario, @Param("excluirId") Integer excluirId);


    List<Vehiculo> findByUsuarioIdAndEstado(Usuario usuarioId, String estado);

    List<Vehiculo> findBySocioAsalariadoIdAndEstadoTrue(Usuario socioAsalariadoId);

    @Query("SELECT COUNT(v) > 0 FROM Vehiculo v WHERE v.socioAsalariadoId = :asalariado AND v.estado = 'ACTIVO'")
    boolean existsBySocioAsalariadoActivo(@Param("asalariado") Usuario asalariado);

    }
  